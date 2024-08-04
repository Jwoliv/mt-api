package com.mt.utils;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.mapper.AccountMapper;
import com.mt.mapper.TransactionMapper;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Transaction;
import com.mt.response.PageElementsResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageElementsResponseBuilder {
    @Setter(onMethod = @__(@Autowired))
    private TransactionMapper transactionMapper;
    @Setter(onMethod = @__(@Autowired))
    private AccountMapper accountMapper;

    public PageElementsResponse<TransactionDashboardDto> buildPageTransactions(Integer pageNumber, Page<Transaction> transactionsPageable) {
        return PageElementsResponse.<TransactionDashboardDto>builder()
                .elements(transactionMapper.mapToDashboardDto(transactionsPageable.getContent()))
                .isPrevPage(isPrevPage(pageNumber))
                .isNextPage(isNextPage(pageNumber, transactionsPageable.getTotalPages()))
                .build();
    }

    public PageElementsResponse<AccountDto> buildPageAccounts(Integer pageNumber, Page<Account> accounts) {
        return PageElementsResponse.<AccountDto>builder()
                .elements(accountMapper.mapToAccountDto(accounts.getContent()))
                .isPrevPage(isPrevPage(pageNumber))
                .isNextPage(isNextPage(pageNumber, accounts.getTotalPages()))
                .build();
    }

    private Boolean isPrevPage(Integer pageNumber) {
        return pageNumber > 0;
    }

    private Boolean isNextPage(Integer pageNumber, Integer totalPages) {
        return totalPages > pageNumber + 1;
    }
}
