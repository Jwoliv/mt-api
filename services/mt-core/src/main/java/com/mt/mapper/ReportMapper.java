package com.mt.mapper;

import com.mt.dto.DailyAmountReportDto;
import com.mt.dto.DailyReportDto;
import com.mt.model.DailyAmountReport;
import com.mt.repository.view.DailyReportView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    List<DailyReportDto> toDailyReportDto(List<DailyReportView> dailyReportView);
    List<DailyAmountReportDto> toDailyAmountReportDto(List<DailyAmountReport> amountReports);
}
