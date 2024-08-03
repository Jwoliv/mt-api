package com.mt.service;

import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.ChangeTransactionRequest;

public interface TransactionDbService {
    CreatedTransaction createNewTransaction(String auth, ChangeTransactionRequest request);
    TransactionDto updateTransaction(String auth, ChangeTransactionRequest request);
}
