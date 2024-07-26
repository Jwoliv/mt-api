package com.mt.response;

import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyAmountReportDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SummaryResponse {
    private List<DailyAmountReportDto> dailyReports;
    private List<ProfitReportDto> profitReports;
}
