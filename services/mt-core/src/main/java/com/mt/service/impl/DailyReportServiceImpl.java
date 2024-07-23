package com.mt.service.impl;

import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;
import com.mt.mapper.ReportMapper;
import com.mt.repository.TransactionRepository;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.DailyReportServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class DailyReportServiceImpl implements DailyReportServiceI {
    private static final Long DEFAULT_STOCK_DAYS = 20L;
    private static final Long DEFAULT_AMOUNT_DAILY_REPORTS = 60L;


    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private ReportMapper reportMapper;

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

}
