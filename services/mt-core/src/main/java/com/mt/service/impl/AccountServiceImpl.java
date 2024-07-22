package com.mt.service.impl;

import com.mt.dto.AccountDto;
import com.mt.mapper.AccountMapper;
import com.mt.model.transaction.Account;
import com.mt.repository.AccountRepository;
import com.mt.repository.UserRepository;
import com.mt.request.NewAccountRequest;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.AccountServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountServiceI {

    @Setter(onMethod = @__(@Autowired))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__(@Autowired))
    private AccountMapper accountMapper;
    @Setter(onMethod = @__(@Autowired))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__(@Autowired))
    private UserRepository userRepository;


    @Override
    public AccountDto createAccount(String authorization, NewAccountRequest request) {
        var email = provider.extractEmail(authorization);
        var account = Account.builder()
                .name(request.getName())
                .currentBalance(request.getStartBalance())
                .spendMoney(new BigDecimal("0"))
                .earnMoney(new BigDecimal("0"))
                .createdAt(LocalDateTime.now().toLocalDate().atStartOfDay())
                .updatedAt(LocalDateTime.now().toLocalDate().atStartOfDay())
                .user(userRepository.findByEmail(email).orElse(null))
                .build();

        var savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccountsByEmail(String authorization, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(authorization);
        return accountMapper.toDto(accountRepository.findAccountByEmail(email, PageRequest.of(pageNumber, pageSize)));
    }

    @Override
    public List<AccountDto> getAccountsDashboard(String authorization) {
        var email = provider.extractEmail(authorization);
        return accountMapper.toDto(accountRepository.getAccountsDashboard(email));
    }
}
