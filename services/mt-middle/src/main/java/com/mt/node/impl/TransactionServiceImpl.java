package com.mt.node.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.feign.TransactionCore;
import com.mt.node.TransactionService;
import com.mt.request.NewTransactionRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionCore transactionCore;

    @Override
    public List<TransactionDashboardDto> getTransactionsDashboard(String auth) {
        return transactionCore.getTransactionsDashboard(auth);
    }

    @Override
    public List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize) {
        return transactionCore.getTransactions(auth, pageNumber, pageSize);
    }

    @Override
    public CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request) {
        return transactionCore.createNewTransaction(auth, request);
    }
}
