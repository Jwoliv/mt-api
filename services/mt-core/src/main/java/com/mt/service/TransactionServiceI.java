package com.mt.service;

import com.mt.dto.CreatedTransaction;
import com.mt.dto.TransactionDashboardDto;
import com.mt.model.transaction.Transaction;
import com.mt.request.NewTransactionRequest;

import java.util.List;

public interface TransactionServiceI {
    List<TransactionDashboardDto> getTransactionsDashboard(String auth);
    List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize);
    CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request);
}
