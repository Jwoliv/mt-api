package com.mt.service.impl.generate_reports;

import java.io.ByteArrayInputStream;

public interface ReportGenerator {
    ByteArrayInputStream getTransactionReports(String email);
    ByteArrayInputStream getAccountReports(String email);
    ByteArrayInputStream getDailyReports(String email);
    ByteArrayInputStream getDailyAmountReports(String email);
    ByteArrayInputStream getAllReports(String email);
}
