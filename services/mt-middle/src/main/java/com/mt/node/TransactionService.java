package com.mt.node;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;

import java.util.List;

public interface TransactionService {
    List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize);
    CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request);
    TransactionDto getUserTransactionById(String auth, Long id);
    void deleteTransactionById(String auth, Long id);
    List<TransactionDashboardDto> getTransactionByAccountId(String auth, Long id, Integer pageNumber, Integer pageSize);
}
