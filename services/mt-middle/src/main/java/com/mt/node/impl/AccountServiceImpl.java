package com.mt.node.impl;

import com.mt.dto.form_dto.AccountFormDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.feign.AccountCore;
import com.mt.node.AccountService;
import com.mt.request.NewAccountRequest;
import com.mt.request.UpdateAccountRequest;
import com.mt.response.PageElementsResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Setter(onMethod = @__({@Autowired}))
    private AccountCore accountCore;

    @Override
    public AccountDto createAccount(String authorization, NewAccountRequest accountDto) {
        return accountCore.createAccount(authorization, accountDto);
    }

    @Override
    public PageElementsResponse<AccountDto> getAllAccountsByEmailPageable(String authorization, Integer pageNumber, Integer pageSize) {
        return accountCore.getAllAccountsByEmailPageable(authorization, pageNumber, pageSize);
    }

    @Override
    public List<AccountDto> getAccountsDashboard(String authorization) {
        return accountCore.getAccountsDashboard(authorization);
    }

    @Override
    public List<AccountFormDto> getAccountsByEmailForNewTransaction(String authorization) {
        return accountCore.getAccountsByEmailForNewTransaction(authorization);
    }

    @Override
    public AccountDto getUserAccountById(String auth, Long id) {
        return accountCore.getUserAccountById(auth, id);
    }

    @Override
    public void deleteAccountById(String auth, Long id) {
        accountCore.deleteAccountById(auth, id);
    }

    @Override
    public AccountDto updateAccountById(String auth, Long id, UpdateAccountRequest request) {
        return accountCore.updateAccountById(auth, id, request);
    }

}
