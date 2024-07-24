package com.mt.service;

import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;

public interface CreateTransactionService {
    CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request);
}
