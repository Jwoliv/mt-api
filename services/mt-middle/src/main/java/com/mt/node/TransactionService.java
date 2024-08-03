package com.mt.node;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.ChangeTransactionRequest;
import com.mt.response.PageElementsResponse;

public interface TransactionService {
    PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize);
    CreatedTransaction createNewTransaction(String auth, ChangeTransactionRequest request);
    TransactionDto getUserTransactionById(String auth, Long id);
    void deleteTransactionById(String auth, Long id);
    PageElementsResponse<TransactionDashboardDto> getTransactionByAccountId(String auth, Long id, Integer pageNumber, Integer pageSize);
    TransactionDto updateTransactionById(String auth, Long id, ChangeTransactionRequest transaction);
}
