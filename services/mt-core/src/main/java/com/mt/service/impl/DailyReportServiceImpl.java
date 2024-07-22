package com.mt.service.impl;

import com.mt.dto.DailyReportDto;
import com.mt.mapper.ReportMapper;
import com.mt.repository.TransactionRepository;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.DailyReportServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyReportServiceImpl implements DailyReportServiceI {

    private static final Long DEFAULT_STOCK_DAYS = 20L;


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
        return reportMapper.toDto(reports);
    }

}
