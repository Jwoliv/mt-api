package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.form_dto.AccountFormDto;
import com.mt.dto.model_dto.AccountDto;
import com.mt.request.NewAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "accounts",
        url="${mt-middle.endpoints.accounts}",
        configuration = FeignConfig.class
)
public interface AccountCore {
    @PostMapping("/new")
    AccountDto createAccount(@RequestHeader("Authorization") String authorization, @RequestBody NewAccountRequest account);

    @GetMapping
    List<AccountDto> getAllAccountsByEmail(@RequestHeader("Authorization") String authorization,
                                           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize);


    @GetMapping("/form-data")
    List<AccountFormDto> getAccountsByEmailForNewTransaction(@RequestHeader("Authorization") String authorization);

    @GetMapping("/dashboard")
    List<AccountDto> getAccountsDashboard(@RequestHeader("Authorization") String authorization);
}