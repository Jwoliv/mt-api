package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.enums.TypeTransaction;
import com.mt.mapper.TransactionMapper;
import com.mt.model.transaction.Transaction;
import com.mt.repository.AccountRepository;
import com.mt.repository.CategoryRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import com.mt.repository.view.TransactionDashboardView;
import com.mt.request.NewTransactionRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.TransactionServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionServiceI {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;
    @Setter(onMethod = @__({@Autowired}))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;


    @Override
    public List<TransactionDashboardDto> getTransactionsDashboard(String auth) {
        return getTransactionsPageable(auth, 0, 3);
    }

    @Override
    public List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize) {
        return getTransactionsPageable(auth, pageNumber, pageSize);
    }

    @Override
    public CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request) {
        String email = provider.extractEmail(auth);

        Transaction transaction;

        if (List.of(TypeTransaction.EARNING, TypeTransaction.SPENDING).contains(request.getType())) {
            transaction = Transaction.builder()
                    .date(request.getDate().atStartOfDay())
                    .amount(request.getAmount())
                    .type(request.getType())
                    .user(userRepository.findByEmail(email).orElse(null))
                    .category(categoryRepository.findById(request.getCategoryId()).orElse(null))
                    .account(accountRepository.findById(request.getAccountId()).orElse(null))
                    .createdAt(request.getDate().atStartOfDay())
                    .sender(request.getSender())
                    .note(request.getNote())
                    .build();
        } else {
            transaction = Transaction.builder()
                    .date(request.getDate().atStartOfDay())
                    .amount(request.getAmount())
                    .type(request.getType())
                    .user(userRepository.findByEmail(email).orElse(null))
                    .account(accountRepository.findById(request.getSenderAccount()).orElse(null))
                    .receiverAccount(accountRepository.findById(request.getReceiverAccount()).orElse(null))
                    .createdAt(request.getDate().atStartOfDay())
                    .category(categoryRepository.findById(3L).orElse(null)) //todo: id 3 have a transfer category
                    .build();
        }


        Transaction savedTransaction = transactionRepository.save(transaction);

        return CreatedTransaction.builder()
                .id(savedTransaction.getId())
                .accountName(savedTransaction.getAccount().getName())
                .categoryName(savedTransaction.getCategory().getName())
                .amount(savedTransaction.getAmount())
                .date(savedTransaction.getDate())
                .build();
    }

    private List<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize) {
        String email = provider.extractEmail(auth);
        List<TransactionDashboardView> transactionDashboardViews = transactionRepository.getTransactionsDashboard(email, PageRequest.of(pageNumber, pageSize));
        return transactionMapper.viewToDto(transactionDashboardViews);
    }
}
