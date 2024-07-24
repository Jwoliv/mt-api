package com.mt.controller;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.NewTransactionRequest;
import com.mt.service.TransactionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Setter(onMethod = @__({@Autowired}))
    private TransactionService transactionService;


    @PostMapping("/new")
    public CreatedTransaction createNewTransaction(@RequestHeader("Authorization") String auth,
                                                   @RequestBody NewTransactionRequest request
    ) {
        return transactionService.createNewTransaction(auth, request);
    }

    @GetMapping
    public List<TransactionDashboardDto> getTransactions(@RequestHeader("Authorization") String auth,
                                                             @RequestParam Integer pageNumber,
                                                             @RequestParam Integer pageSize
    ) {
        return transactionService.getTransactions(auth, pageNumber, pageSize);
    }

    @GetMapping("/dashboard")
    public List<TransactionDashboardDto> getTransactionsDashboard(@RequestHeader("Authorization") String auth) {
        return transactionService.getTransactionsDashboard(auth);
    }
}
