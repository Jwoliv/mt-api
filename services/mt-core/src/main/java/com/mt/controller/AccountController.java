package com.mt.controller;

import com.mt.dto.AccountDto;
import com.mt.model.transaction.Account;
import com.mt.request.NewAccountRequest;
import com.mt.service.AccountService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Setter(onMethod = @__({@Autowired}))
    private AccountService accountService;

    @PostMapping("/new")
    public AccountDto createAccount(
            @RequestHeader("Authorization") String authorization,
            @RequestBody NewAccountRequest account
    ) {
        return accountService.createAccount(authorization, account);
    }

    @GetMapping
    public List<AccountDto> getAllAccountsByEmail(@RequestHeader("Authorization") String authorization) {
        return accountService.getAllAccountsByEmail(authorization);
    }

    @GetMapping("/dashboard")
    public List<AccountDto> getAccountsDashboard(@RequestHeader("Authorization") String authorization) {
        return accountService.getAccountsDashboard(authorization);
    }
}
