package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "transactions",
        url="${feign.client.config.mt-core-service.path.transaction}",
        configuration = FeignConfig.class
)
public interface TransactionCore {
    @PostMapping("/new")
    CreatedTransaction createNewTransaction(@RequestHeader("Authorization") String auth,
                                            @RequestBody NewTransactionRequest request
    );

    @GetMapping
    List<TransactionDashboardDto> getTransactions(@RequestHeader("Authorization") String auth,
                                                  @RequestParam Integer pageNumber,
                                                  @RequestParam Integer pageSize
    );

    @GetMapping("/dashboard")
    List<TransactionDashboardDto> getTransactionsDashboard(@RequestHeader("Authorization") String auth);
}
