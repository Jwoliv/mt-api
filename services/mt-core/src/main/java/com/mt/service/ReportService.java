package com.mt.service;

import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;

import java.util.List;

public interface ReportService {
    List<DailyReportDto> getDailyReports(String authorization);
    List<DailyAmountReportDto> getDailyAmountReports(String authorization);
    List<ProfitReportDto> getProfitReports(String authorization);
    void saveDailyAmountReport();
}
