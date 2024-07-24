package com.mt.service;

import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;

import java.util.List;

public interface DailyReportService {
    List<DailyReportDto> getDailyReports(String authorization);
    List<DailyAmountReportDto> getDailyAmountReports(String authorization);
}
