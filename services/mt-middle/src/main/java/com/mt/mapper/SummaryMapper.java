package com.mt.mapper;

import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.response.SummaryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SummaryMapper {
    SummaryResponse toSummaryResponse(List<DailyAmountReportDto> dailyReports, List<ProfitReportDto> profitReports);
}
