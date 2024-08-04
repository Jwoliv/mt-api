package com.mt.controller;

import com.mt.security.UserAuthenticationProvider;
import com.mt.service.impl.generate_reports.ExcelReportGenerator;
import com.mt.service.impl.generate_reports.ReportGenerator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download/reports")
public class GenerateReportController {

    @Setter(onMethod = @__({@Autowired}))
    @Qualifier("excelReportGenerator")
    private ReportGenerator excelReportGenerator;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;

    @GetMapping("/xlsx")
    public ResponseEntity<InputStreamResource> downloadAllReports(@RequestHeader String auth) {
        var in = excelReportGenerator.getAllReports(provider.extractEmail(auth));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=all_reports.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }


}
