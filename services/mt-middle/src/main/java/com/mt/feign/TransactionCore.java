package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;
import com.mt.response.PageElementsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "transactions",
        url="${mt-middle.endpoints.transaction}",
        configuration = FeignConfig.class
)
public interface TransactionCore {
    @PostMapping("/new")
    CreatedTransaction createNewTransaction(@RequestHeader("Authorization") String auth,
                                            @RequestBody NewTransactionRequest request
    );

    @GetMapping
    PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(@RequestHeader("Authorization") String auth,
                                                                          @RequestParam Integer pageNumber,
                                                                          @RequestParam Integer pageSize
    );

    @GetMapping("/dashboard")
    List<TransactionDashboardDto> getTransactionsDashboard(@RequestHeader("Authorization") String auth);

    @GetMapping("/{id}")
    TransactionDto getUserTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    void deleteTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id);

    @GetMapping("/account/{id}")
    PageElementsResponse<TransactionDashboardDto> getTransactionByAccountId(@RequestHeader("Authorization") String auth,
                                                                                   @PathVariable("id") Long id,
                                                                                   @RequestParam Integer pageNumber,
                                                                                   @RequestParam Integer pageSize);
}
