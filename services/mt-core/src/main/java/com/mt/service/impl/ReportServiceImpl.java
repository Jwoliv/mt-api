package com.mt.service.impl;

import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;
import com.mt.enums.ProfitReportPeriod;
import com.mt.mapper.DailyAmountReportMapper;
import com.mt.mapper.ReportMapper;
import com.mt.model.DailyAmountReport;
import com.mt.model.transaction.Account;
import com.mt.repository.AccountRepository;
import com.mt.repository.DailyAmountReportRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.view.DailyAmountReportView;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.ReportService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Long DEFAULT_STOCK_DAYS = 20L;
    private static final Long DEFAULT_AMOUNT_DAILY_REPORTS = 60L;


    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private ReportMapper reportMapper;
    @Setter(onMethod = @__({@Autowired}))
    private DailyAmountReportMapper dailyAmountReportMapper;
    @Setter(onMethod = @__({@Autowired}))
    private DailyAmountReportRepository dailyAmountReportRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;

    @Override
    public List<DailyReportDto> getDailyReports(String authorization) {
        var currentDate = LocalDateTime.now();
        var email = provider.extractEmail(authorization);
        var reports = transactionRepository.getDailyUserReport(email, currentDate.minusDays(DEFAULT_STOCK_DAYS), currentDate);
        return reportMapper.toDailyReportDto(reports);
    }

    @Override
    public List<DailyAmountReportDto> getDailyAmountReports(String authorization) {
        var currentDate = LocalDateTime.now();
        var startDate = currentDate.minusDays(DEFAULT_AMOUNT_DAILY_REPORTS);
        var email = provider.extractEmail(authorization);
        var reports = transactionRepository.getDailyAmountReports(email, startDate, currentDate, PageRequest.of(0, 60)).reversed();
        var reportsResponse = reportMapper.toDailyAmountReportDto(reports);
        IntStream.range(0, reportsResponse.size()).forEach(i -> reportsResponse.get(i).setIndex(i + 1L));
        return reportsResponse;
    }

    @Override
    public List<ProfitReportDto> getProfitReports(String authorization) {
        var email = provider.extractEmail(authorization);
        var generalAmount = getGeneralAmount(email);
        return List.of(
                generateProfitReport(transactionRepository.getYearProfit(email), generalAmount, ProfitReportPeriod.YEAR),
                generateProfitReport(transactionRepository.getMonthProfit(email), generalAmount, ProfitReportPeriod.MONTH),
                generateProfitReport(transactionRepository.getWeekProfit(email), generalAmount, ProfitReportPeriod.WEEK),
                generateProfitReport(transactionRepository.getDayProfit(email), generalAmount, ProfitReportPeriod.DAY)
        );
    }

    private ProfitReportDto generateProfitReport(BigDecimal profit, BigDecimal amount, ProfitReportPeriod period) {
        return reportMapper.toProfitReportDto(profit, getProfitPercentage(profit, amount), period);
    }

    private BigDecimal getProfitPercentage(BigDecimal profit, BigDecimal generalAmount) {
        return (profit.multiply(new BigDecimal("100")))
                .divide(generalAmount, 2, RoundingMode.HALF_UP);
    }

    @Override
    public void saveDailyAmountReport() {
        var dailyAmountReports = getDailyAmountReports();
        dailyAmountReportRepository.saveAll(dailyAmountReports);
    }

    private List<DailyAmountReport> getDailyAmountReports() {
        var dbData = dailyAmountReportRepository.findSumOfAccountBalancesGroupedByUserId(LocalDateTime.now().minusDays(1));
        var dailyAmountReports = convertToView(dbData);
        return dailyAmountReportMapper.toDailyAmountReportViews(dailyAmountReports);
    }

    private List<DailyAmountReportView> convertToView(List<Object[]> data) {
        return data.stream()
                .map(item -> (DailyAmountReportView) item[1])
                .toList();
    }

    private BigDecimal getGeneralAmount(String email) {
        return accountRepository.findAccountsByEmail(email).stream()
                .map(Account::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
