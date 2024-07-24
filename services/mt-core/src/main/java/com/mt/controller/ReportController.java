package com.mt.controller;

import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;
import com.mt.service.DailyReportService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    @Setter(onMethod = @__({@Autowired}))
    private DailyReportService dailyReportService;

    @GetMapping("/daily-dashboard")
    public List<DailyReportDto> getDailyReports(@RequestHeader("Authorization") String authorization) {
        return dailyReportService.getDailyReports(authorization);
    }

    @GetMapping("/daily-amount-reports")
    public List<DailyAmountReportDto> getDailyAmountReports(@RequestHeader("Authorization") String authorization) {
        return dailyReportService.getDailyAmountReports(authorization);
    }
}
