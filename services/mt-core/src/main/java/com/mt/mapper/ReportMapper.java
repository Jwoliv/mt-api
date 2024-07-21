package com.mt.mapper;

import com.mt.dto.DailyReportDto;
import com.mt.repository.view.DailyReportView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    List<DailyReportDto> toDto(List<DailyReportView> dailyReportView);
}
