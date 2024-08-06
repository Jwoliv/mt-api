package com.mt.controller;

import com.mt.security.UserAuthenticationProvider;
import com.mt.service.impl.generate_reports.ReportGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/download-reports")
public class GeneratorReportController {

    @Setter(onMethod = @__({@Autowired}))
    @Qualifier("excelReportGenerator")
    private ReportGenerator excelReportGenerator;

    @Setter(onMethod = @__({@Autowired}))
    @Qualifier("csvReportGenerator")
    private ReportGenerator csvReportGenerator;

    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;

    @GetMapping("/xlsx")
    public void downloadAllReportsAsXlsx(@RequestHeader("Authorization") String auth, HttpServletResponse response) {
        excelReportGenerator.getAllReports(provider.extractEmail(auth), response);
    }

    @GetMapping("/csv")
    public void downloadAllReportsAsCsv(@RequestHeader("Authorization") String auth, HttpServletResponse response) {
        csvReportGenerator.getAllReports(provider.extractEmail(auth), response);
    }
}
