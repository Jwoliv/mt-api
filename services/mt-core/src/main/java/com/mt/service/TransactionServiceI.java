package com.mt.service;

import com.mt.dto.TransactionDashboardDto;

import java.util.List;

public interface TransactionServiceI {
    List<TransactionDashboardDto> getTransactionsDashboard(String auth);
}
