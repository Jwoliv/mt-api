package com.mt.service.impl;

import com.mt.dto.form_dto.AccountFormDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.exception.NotFoundException;
import com.mt.mapper.AccountMapper;
import com.mt.model.User;
import com.mt.model.transaction.Account;
import com.mt.repository.AccountRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import com.mt.request.NewAccountRequest;
import com.mt.request.UpdateAccountRequest;
import com.mt.response.PageElementsResponse;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.AccountService;
import com.mt.utils.PageElementsResponseBuilder;
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
    @Setter(onMethod = @__({@Autowired}))
    private PageElementsResponseBuilder pageElementsResponseBuilder;


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
                .user(userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(User.class, email)))
                .build();

        var savedAccount = accountRepository.save(account);
        return accountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public PageElementsResponse<AccountDto> getAllAccountsByEmailPageable(String authorization, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(authorization);
        var accounts = accountRepository.findAccountsByEmail(email, PageRequest.of(pageNumber, pageSize));
        return pageElementsResponseBuilder.buildPageAccounts(pageNumber, accounts);
    }

    @Override
    public List<AccountDto> getAccountsDashboard(String authorization) {
        var email = provider.extractEmail(authorization);
        return accountMapper.mapToAccountDto(accountRepository.getAccountsDashboard(email));
    }

    @Override
    public List<AccountFormDto> getAccountsByEmailForNewTransaction(String authorization) {
        var email = provider.extractEmail(authorization);
        return accountMapper.mapToAccountFormDto(accountRepository.findAccountsByEmail(email));
    }

    @Override
    public AccountDto getUserAccountById(String auth, Long id) {
        var email = provider.extractEmail(auth);
        var account = accountRepository.getUserAccountById(email, id).orElseThrow(() -> new NotFoundException(Account.class, email.concat(" ").concat(id.toString())));
        return accountMapper.mapToAccountDto(account);
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
        var account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException(Account.class, id));
        return accountMapper.mapToAccountDto(account);
    }

}
