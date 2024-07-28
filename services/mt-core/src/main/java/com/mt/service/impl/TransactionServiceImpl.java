package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.mapper.TransactionMapper;
import com.mt.repository.TransactionRepository;
import com.mt.request.NewTransactionRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CreateTransactionService;
import com.mt.service.TransactionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;
    @Setter(onMethod = @__({@Autowired}))
    private CreateTransactionService createTransactionService;


    @Override
    public List<TransactionDashboardDto> getTransactionsDashboard(String auth) {
        return getTransactionsPageable(auth, 0, 3);
    }

    @Override
    public List<TransactionDashboardDto> getTransactions(String auth, Integer pageNumber, Integer pageSize) {
        return getTransactionsPageable(auth, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public CreatedTransaction createNewTransaction(String auth, NewTransactionRequest request) {
        return createTransactionService.createNewTransaction(auth, request);
    }

    @Override
    public TransactionDto getUserTransactionById(String auth, Long id) {
        var transaction = transactionRepository.getUserTransactionById(provider.extractEmail(auth), id);
        return transactionMapper.toTransactionDto(transaction);
    }

    @Override
    @Transactional
    public void deleteTransactionById(String auth, Long id) {
        var email = provider.extractEmail(auth);
        transactionRepository.deleteTransactionById(email, id);
    }

    private List<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(auth);
        var transactionDashboardViews = transactionRepository.getTransactionsDashboard(email, PageRequest.of(pageNumber, pageSize));
        return transactionMapper.viewToDto(transactionDashboardViews);
    }
}
