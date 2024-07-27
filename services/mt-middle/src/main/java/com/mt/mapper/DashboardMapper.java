package com.mt.mapper;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.dto.reports.DailyReportDto;
import com.mt.response.DashboardResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DashboardMapper {
    DashboardResponse toDashboardResponse(List<AccountDto> accounts, List<DailyReportDto> reports, List<TransactionDashboardDto> transactions);
}
