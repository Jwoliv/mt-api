package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.mapper.TransactionMapper;
import com.mt.model.transaction.Transaction;
import com.mt.repository.TransactionRepository;
import com.mt.repository.view.TransactionDashboardView;
import com.mt.request.NewTransactionRequest;
import com.mt.response.PageElementsResponse;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CreateTransactionService;
import com.mt.service.TransactionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        var transaction = getTransactionPageable(auth, PageRequest.of(0, 3)).getContent();
        return transactionMapper.mapToDashboardDto(transaction);
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

    @Override
    public List<TransactionDashboardDto> getTransactionByAccountId(String auth, Long id, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(auth);
        var transactions = transactionRepository.getTransactionByAccountId(email, id, PageRequest.of(pageNumber, pageSize));
        return transactionMapper.mapToDashboardDto(transactions);
    }

    @Override
    public PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize) {
        var transactionsPageable = getTransactionPageable(auth, PageRequest.of(pageNumber, pageSize));
        return PageElementsResponse.<TransactionDashboardDto>builder()
                .elements(transactionMapper.mapToDashboardDto(transactionsPageable.getContent()))
                .isPrevPage(pageNumber > 0)
                .isNextPage(transactionsPageable.getTotalPages() > (pageNumber + 1))
                .build();
    }

    private Page<Transaction> getTransactionPageable(String auth, Pageable pageable) {
        var email = provider.extractEmail(auth);
        return transactionRepository.getTransactionsPageable(email, pageable);
    }
}
