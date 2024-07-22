package com.mt.service;

import com.mt.dto.TransactionDashboardDto;

import java.util.List;

public interface TransactionServiceI {
    List<TransactionDashboardDto> getTransactionsDashboard(String auth);
    List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize);
}
