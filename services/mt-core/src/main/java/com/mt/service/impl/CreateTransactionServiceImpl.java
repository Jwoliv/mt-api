package com.mt.service.impl;

import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.enums.TypeTransaction;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Transaction;
import com.mt.repository.AccountRepository;
import com.mt.repository.CategoryRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import com.mt.request.NewTransactionRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CreateTransactionService;
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

    @Override
    public CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request) {
        var email = provider.extractEmail(auth);
        var transaction = proceedTransaction(request, email);
        var savedTransaction = transactionRepository.save(transaction);
        return buildCreatedTransaction(savedTransaction);
    }

    private Transaction proceedTransaction(NewTransactionRequest request, String email) {
        return isUsualTransaction(request)
                ? generateUsualTransaction(request, email)
                : generateTransferTransaction(request, email);
    }

    private Transaction generateTransferTransaction(NewTransactionRequest request, String email) {
        var account = accountRepository.findById(request.getSenderAccount()).orElse(null);
        var receiver = accountRepository.findById(request.getReceiverAccount()).orElse(null);
        var transaction = buildTransferTransaction(request, email, account, receiver);
        updateAccountsByTransferTransaction(request, account, receiver);
        return transaction;
    }

    private Transaction generateUsualTransaction(NewTransactionRequest request, String email) {
        var account = accountRepository.findById(request.getAccountId()).orElse(null);
        var transaction = buildUsualTransaction(request, email, account);
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

    private CreatedTransaction buildCreatedTransaction(Transaction savedTransaction) {
        return CreatedTransaction.builder()
                .id(savedTransaction.getId())
                .accountName(savedTransaction.getAccount().getName())
                .categoryName(savedTransaction.getCategory().getName())
                .amount(savedTransaction.getAmount())
                .date(savedTransaction.getDate())
                .type(savedTransaction.getType())
                .build();
    }

    private Transaction buildUsualTransaction(NewTransactionRequest request, String email, Account account) {
        return Transaction.builder()
                .date(request.getDate().atStartOfDay())
                .amount(request.getAmount())
                .type(request.getType())
                .user(userRepository.findByEmail(email).orElse(null))
                .category(categoryRepository.findById(request.getCategoryId()).orElse(null))
                .account(account)
                .createdAt(request.getDate().atStartOfDay())
                .sender(request.getSender())
                .note(request.getNote())
                .build();
    }

    private Transaction buildTransferTransaction(NewTransactionRequest request, String email, Account account, Account receiver) {
        return Transaction.builder()
                .date(request.getDate().atStartOfDay())
                .amount(request.getAmount())
                .type(request.getType())
                .user(userRepository.findByEmail(email).orElse(null))
                .account(account)
                .receiverAccount(receiver)
                .createdAt(request.getDate().atStartOfDay())
                .category(categoryRepository.findById(3L).orElse(null)) //todo: id 3 have a transfer category
                .build();
    }
}
