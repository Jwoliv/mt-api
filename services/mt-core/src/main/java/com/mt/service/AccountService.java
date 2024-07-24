package com.mt.service;

import com.mt.dto.model_dto.AccountDto;
import com.mt.dto.form_dto.AccountFormDto;
import com.mt.request.NewAccountRequest;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(String authorization, NewAccountRequest accountDto);
    List<AccountDto> getAllAccountsByEmail(String authorization, Integer pageNumber, Integer pageSize);
    List<AccountDto> getAccountsDashboard(String authorization);
    List<AccountFormDto> getAccountsByEmailForNewTransaction(String authorization);
}
