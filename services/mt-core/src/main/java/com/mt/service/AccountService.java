package com.mt.service;

import com.mt.dto.model_dto.AccountDto;
import com.mt.dto.form_dto.AccountFormDto;
import com.mt.request.NewAccountRequest;
import com.mt.request.UpdateAccountRequest;
import com.mt.response.PageElementsResponse;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(String authorization, NewAccountRequest accountDto);
    PageElementsResponse<AccountDto> getAllAccountsByEmailPageable(String authorization, Integer pageNumber, Integer pageSize);
    List<AccountDto> getAccountsDashboard(String authorization);
    List<AccountFormDto> getAccountsByEmailForNewTransaction(String authorization);
    AccountDto getUserAccountById(String email, Long id);
    void deleteAccountById(String auth, Long id);
    AccountDto updateAccountById(String auth, Long id, UpdateAccountRequest request);
}
