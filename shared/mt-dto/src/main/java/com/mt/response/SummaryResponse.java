package com.mt.response;

import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyReportDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SummaryResponse {
    private List<DailyReportDto> dailyReports;
    private List<ProfitReportDto> profitsReports;
}
