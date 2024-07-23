package com.mt.service;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;

import java.util.List;

public interface TransactionServiceI {
    List<TransactionDashboardDto> getTransactionsDashboard(String auth);
    List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize);
    CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request);
}
