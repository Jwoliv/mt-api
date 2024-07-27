package com.mt.controller;

import com.mt.dto.form_dto.AccountFormDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.node.AccountService;
import com.mt.request.NewAccountRequest;
import jakarta.websocket.server.PathParam;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/middle/accounts")
public class AccountController {

    @Setter(onMethod = @__({@Autowired}))
    private AccountService accountService;

    @PostMapping("/new")
    public AccountDto createAccount(@RequestHeader("Authorization") String authorization,
                                    @RequestBody NewAccountRequest account
    ) {
        return accountService.createAccount(authorization, account);
    }

    @GetMapping
    public List<AccountDto> getAllAccountsByEmail(@RequestHeader("Authorization") String authorization,
                                                  @PathParam(value = "pageNumber") Integer pageNumber,
                                                  @PathParam(value = "pageSize") Integer pageSize
    ) {
        return accountService.getAllAccountsByEmail(authorization, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public AccountDto getUserAccountById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        return accountService.getUserAccountById(auth, id);
    }


    @GetMapping("/form-data")
    public List<AccountFormDto> getAccountsByEmailForNewTransaction(@RequestHeader("Authorization") String authorization) {
        return accountService.getAccountsByEmailForNewTransaction(authorization);
    }

    @GetMapping("/dashboard")
    public List<AccountDto> getAccountsDashboard(@RequestHeader("Authorization") String authorization) {
        return accountService.getAccountsDashboard(authorization);
    }
}
