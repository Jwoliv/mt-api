package com.mt.service;

import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.ChangeTransactionRequest;

public interface CreateTransactionService {
    CreatedTransaction createNewTransaction(String auth, ChangeTransactionRequest request);
}
