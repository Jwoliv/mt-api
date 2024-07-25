package com.mt.mapper;

import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;
import com.mt.enums.ProfitReportPeriod;
import com.mt.model.DailyAmountReport;
import com.mt.repository.view.DailyReportView;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    List<DailyReportDto> toDailyReportDto(List<DailyReportView> dailyReportView);
    List<DailyAmountReportDto> toDailyAmountReportDto(List<DailyAmountReport> amountReports);
    ProfitReportDto toProfitReportDto(BigDecimal profit, BigDecimal percentage, ProfitReportPeriod period);
}
