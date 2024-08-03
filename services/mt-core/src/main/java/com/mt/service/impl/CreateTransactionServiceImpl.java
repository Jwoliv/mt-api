package com.mt.service.impl;

import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.enums.TypeOperation;
import com.mt.mapper.TransactionMapper;
import com.mt.repository.TransactionRepository;
import com.mt.request.ChangeTransactionRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CreateTransactionService;
import com.mt.service.GeneratorTransaction;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionServiceImpl implements CreateTransactionService {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;
    @Setter(onMethod = @__({@Autowired}))
    private GeneratorTransaction generatorTransaction;

    @Override
    @Transactional
    public CreatedTransaction createNewTransaction(String auth, ChangeTransactionRequest request, TypeOperation operation) {
        var email = provider.extractEmail(auth);
        var transaction = generatorTransaction.getTransaction(request, email, operation);
        var savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.mapToCreateTransaction(savedTransaction);
    }

}
