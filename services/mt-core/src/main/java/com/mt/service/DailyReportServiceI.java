package com.mt.service;

import com.mt.dto.DailyAmountReportDto;
import com.mt.dto.DailyReportDto;

import java.util.List;

public interface DailyReportServiceI {
    List<DailyReportDto> getDailyReports(String authorization);
    List<DailyAmountReportDto> getDailyAmountReports(String authorization);
}
