package com.mt.mapper;

import com.mt.model.DailyAmountReport;
import com.mt.repository.view.DailyAmountReportView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyAmountReportMapper {
    List<DailyAmountReport> toDailyAmountReportViews(List<DailyAmountReportView> dailyAmountReports);
}
