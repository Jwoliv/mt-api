package com.mt.service.impl;

import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.enums.TypeOperation;
import com.mt.mapper.TransactionMapper;
import com.mt.model.transaction.Transaction;
import com.mt.repository.TransactionRepository;
import com.mt.request.ChangeTransactionRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.TransactionDbService;
import com.mt.service.GeneratorTransaction;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class TransactionDbServiceImpl implements TransactionDbService {

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
    public CreatedTransaction createNewTransaction(String auth, ChangeTransactionRequest request) {
        return changeTransaction(auth, request, (req, email) -> generatorTransaction.getTransaction(req, email, TypeOperation.CREATE), transactionMapper::mapToCreateTransaction);

    }

    @Override
    public TransactionDto updateTransaction(String auth, ChangeTransactionRequest request) {
        return changeTransaction(auth, request, (req, email) -> generatorTransaction.getTransaction(req, email, TypeOperation.UPDATE), transactionMapper::toTransactionDto);

    }

    private <T> T changeTransaction(String auth,
                                    ChangeTransactionRequest request,
                                    BiFunction<ChangeTransactionRequest, String, Transaction> transactionFunction,
                                    Function<Transaction, T> mapperFunction
    ) {
        var email = provider.extractEmail(auth);
        var transaction = transactionFunction.apply(request, email);
        var savedTransaction = transactionRepository.save(transaction);
        return mapperFunction.apply(savedTransaction);
    }


}
