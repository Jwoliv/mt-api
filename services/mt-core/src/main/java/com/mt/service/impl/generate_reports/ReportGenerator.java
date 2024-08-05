package com.mt.service.impl.generate_reports;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportGenerator {
    void getAllReports(String email, HttpServletResponse response);
}
