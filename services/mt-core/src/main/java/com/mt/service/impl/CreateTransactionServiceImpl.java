package com.mt.service.impl;

import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.enums.TypeTransaction;
import com.mt.mapper.TransactionMapper;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Transaction;
import com.mt.repository.AccountRepository;
import com.mt.repository.CategoryRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import com.mt.request.NewTransactionRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CreateTransactionService;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateTransactionServiceImpl implements CreateTransactionService {
    private static final List<TypeTransaction> USUAL_TRANSACTION_TYPES = List.of(TypeTransaction.EARNING, TypeTransaction.SPENDING);

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;

    @Override
    @Transactional
    public CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request) {
        var email = provider.extractEmail(auth);
        var transaction = createTransaction(request, email);
        var savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.mapToCreateTransaction(savedTransaction);
    }

    private Transaction createTransaction(NewTransactionRequest request, String email) {
        return isUsualTransaction(request)
                ? generateUsualTransaction(request, email)
                : generateTransferTransaction(request, email);
    }

    private Transaction generateTransferTransaction(NewTransactionRequest request, String email) {
        var account = accountRepository.findById(request.getSenderAccount()).orElse(null);
        var receiver = accountRepository.findById(request.getReceiverAccount()).orElse(null);
        var user = userRepository.findByEmail(email).orElse(null);
        var transferCategory = categoryRepository.findById(3L).orElse(null);

        var transaction = transactionMapper.mapToTransferTransactionToCreate(request, account, receiver, user, transferCategory);
        updateAccountsByTransferTransaction(request, account, receiver);
        return transaction;
    }

    private Transaction generateUsualTransaction(NewTransactionRequest request, String email) {
        var user = userRepository.findByEmail(email).orElse(null);
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        var account = accountRepository.findById(request.getAccountId()).orElse(null);

        var transaction = transactionMapper.mapToUsualTransactionToCreate(request, user, category, account);
        updateAccountByUsualTransactions(request, account);
        return transaction;
    }

    private Boolean isUsualTransaction(NewTransactionRequest request) {
        return USUAL_TRANSACTION_TYPES.contains(request.getType());
    }

    private void updateAccountsByTransferTransaction(NewTransactionRequest request, Account account, Account receiver) {
        account.changeTransferTransactions(request, receiver);
        accountRepository.saveAll(List.of(account, receiver));
    }

    private void updateAccountByUsualTransactions(NewTransactionRequest request, Account account) {
        account.changeCurrentBalance(request);
        accountRepository.save(account);
    }

}
