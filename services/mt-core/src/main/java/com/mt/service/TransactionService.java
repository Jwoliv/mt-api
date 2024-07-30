package com.mt.service;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;
import com.mt.response.PageElementsResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionDashboardDto> getTransactionsDashboard(String auth);
    PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize);
    CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request);
    TransactionDto getUserTransactionById(String auth, Long id);
    void deleteTransactionById(String auth, Long id);
    List<TransactionDashboardDto> getTransactionByAccountId(String auth, Long id, Integer pageNumber, Integer pageSize);
}
