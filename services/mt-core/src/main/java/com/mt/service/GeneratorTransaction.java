package com.mt.service;

import com.mt.enums.TypeOperation;
import com.mt.model.transaction.Transaction;
import com.mt.request.ChangeTransactionRequest;

public interface GeneratorTransaction {
    Transaction getTransaction(ChangeTransactionRequest request, String email, TypeOperation operation);
}
