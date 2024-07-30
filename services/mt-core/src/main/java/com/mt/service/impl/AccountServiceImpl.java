package com.mt.service.impl;

import com.mt.dto.form_dto.AccountFormDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.mapper.AccountMapper;
import com.mt.model.transaction.Account;
import com.mt.repository.AccountRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import com.mt.request.NewAccountRequest;
import com.mt.request.UpdateAccountRequest;
import com.mt.response.PageElementsResponse;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Setter(onMethod = @__(@Autowired))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__(@Autowired))
    private AccountMapper accountMapper;
    @Setter(onMethod = @__(@Autowired))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__(@Autowired))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;


    @Override
    public AccountDto createAccount(String authorization, NewAccountRequest request) {
        var email = provider.extractEmail(authorization);
        var account = Account.builder()
                .name(request.getName())
                .currentBalance(request.getStartBalance())
                .spendMoney(new BigDecimal("0.00"))
                .earnMoney(new BigDecimal("0.00"))
                .createdAt(LocalDateTime.now().toLocalDate().atStartOfDay())
                .updatedAt(LocalDateTime.now().toLocalDate().atStartOfDay())
                .user(userRepository.findByEmail(email).orElse(null))
                .build();

        var savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public PageElementsResponse<AccountDto> getAllAccountsByEmailPageable(String authorization, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(authorization);
        var accounts = accountRepository.findAccountsByEmail(email, PageRequest.of(pageNumber, pageSize));
        return PageElementsResponse.<AccountDto>builder()
                .elements(accountMapper.toDto(accounts.getContent()))
                .isPrevPage(pageNumber > 0)
                .isNextPage(accounts.getTotalPages() > (pageNumber + 1))
                .build();
    }

    @Override
    public List<AccountDto> getAccountsDashboard(String authorization) {
        var email = provider.extractEmail(authorization);
        return accountMapper.toDto(accountRepository.getAccountsDashboard(email));
    }

    @Override
    public List<AccountFormDto> getAccountsByEmailForNewTransaction(String authorization) {
        var email = provider.extractEmail(authorization);
        return accountMapper.toFormDto(accountRepository.findAccountsByEmail(email));
    }

    @Override
    public AccountDto getUserAccountById(String auth, Long id) {
        var email = provider.extractEmail(auth);
        var account = accountRepository.getUserAccountById(email, id).orElse(null);
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public void deleteAccountById(String auth, Long id) {
        var email = provider.extractEmail(auth);
        transactionRepository.deleteAllByAccountId(id);
        accountRepository.deleteAccountById(email, id);
    }

    @Override
    @Transactional
    public AccountDto updateAccountById(String auth, Long id, UpdateAccountRequest request) {
        var email = provider.extractEmail(auth);
        accountRepository.updateAccountById(email, id, request);
        var account = accountRepository.findById(id).orElse(null);
        return accountMapper.toDto(account);
    }

}
