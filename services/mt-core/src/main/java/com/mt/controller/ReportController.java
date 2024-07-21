package com.mt.controller;

import com.mt.dto.DailyReportDto;
import com.mt.service.DailyReportServiceI;
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
    private DailyReportServiceI dailyReportService;

    @GetMapping("/daily-dashboard")
    public List<DailyReportDto> getDailyReports(@RequestHeader("Authorization") String authorization) {
        return dailyReportService.getDailyReports(authorization);
    }
}
