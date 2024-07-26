package com.mt.response;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.dto.reports.DailyReportDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponse {
    private List<DailyReportDto> reports;
    private List<TransactionDashboardDto> transactions;
    private List<AccountDto> accounts;
}
