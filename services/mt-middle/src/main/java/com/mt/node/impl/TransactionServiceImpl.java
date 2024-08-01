package com.mt.node.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.feign.TransactionCore;
import com.mt.node.TransactionService;
import com.mt.request.NewTransactionRequest;
import com.mt.request.UpdatedTransactionRequest;
import com.mt.response.PageElementsResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionCore transactionCore;

    @Override
    public PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize) {
        return transactionCore.getTransactionsPageable(auth, pageNumber, pageSize);
    }

    @Override
    public CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request) {
        return transactionCore.createNewTransaction(auth, request);
    }

    @Override
    public TransactionDto getUserTransactionById(String auth, Long id) {
        return transactionCore.getUserTransactionById(auth, id);
    }

    @Override
    public void deleteTransactionById(String auth, Long id) {
        transactionCore.deleteTransactionById(auth, id);
    }

    @Override
    public PageElementsResponse<TransactionDashboardDto> getTransactionByAccountId(String auth, Long id, Integer pageNumber, Integer pageSize) {
        return transactionCore.getTransactionByAccountId(auth, id, pageNumber, pageSize);
    }

    @Override
    public TransactionDto updateTransactionById(String auth, Long id, UpdatedTransactionRequest transaction) {
        return transactionCore.updateTransactionById(auth, id, transaction);
    }
}
