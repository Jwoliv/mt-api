package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.enums.TypeTransaction;
import com.mt.mapper.TransactionMapper;
import com.mt.model.transaction.Transaction;
import com.mt.repository.TransactionRepository;
import com.mt.request.NewTransactionRequest;
import com.mt.request.UpdatedTransactionRequest;
import com.mt.response.PageElementsResponse;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CreateTransactionService;
import com.mt.service.TransactionService;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public PageElementsResponse<TransactionDashboardDto> getTransactionByAccountId(String auth, Long id, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(auth);
        var transactionsPageable = transactionRepository.getTransactionByAccountId(email, id, PageRequest.of(pageNumber, pageSize));
        return buildPageTransactions(pageNumber, transactionsPageable);
    }

    @Override
    @Transactional
    public TransactionDto updateTransactionById(String auth, Long id, UpdatedTransactionRequest transactionRequest) {
        if (updateTransaction(auth, id, transactionRequest)) {
            var transaction = transactionRepository.findById(id).orElse(null);
            return transactionMapper.toTransactionDto(transaction);
        }
        return null;
    }

    @Transactional
    public Boolean updateTransaction(String auth, Long id, UpdatedTransactionRequest request) {
        String email = provider.extractEmail(auth);
        Transaction transactionDB = transactionRepository.getUserTransactionById(email, id);
        if (ObjectUtils.notEqual(request.getType(), TypeTransaction.TRANSFER) && transactionDB != null) {
            request.setId(id);
            transactionRepository.updateTransaction(request);
            return true;
        }
        return false;
    }

    private PageElementsResponse<TransactionDashboardDto> buildPageTransactions(Integer pageNumber, Page<Transaction> transactionsPageable) {
        return PageElementsResponse.<TransactionDashboardDto>builder()
                .elements(transactionMapper.mapToDashboardDto(transactionsPageable.getContent()))
                .isPrevPage(pageNumber > 0)
                .isNextPage(transactionsPageable.getTotalPages() > (pageNumber + 1))
                .build();
    }

    @Override
    public PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(String auth, Integer pageNumber, Integer pageSize) {
        var transactionsPageable = getTransactionPageable(auth, PageRequest.of(pageNumber, pageSize));
        return buildPageTransactions(pageNumber, transactionsPageable);
    }

    private Page<Transaction> getTransactionPageable(String auth, Pageable pageable) {
        var email = provider.extractEmail(auth);
        return transactionRepository.getTransactionsPageable(email, pageable);
    }
}
