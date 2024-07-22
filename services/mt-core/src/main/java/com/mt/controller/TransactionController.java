package com.mt.controller;

import com.mt.dto.TransactionDashboardDto;
import com.mt.model.transaction.Transaction;
import com.mt.request.NewTransactionRequest;
import com.mt.service.TransactionServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Setter(onMethod = @__({@Autowired}))
    private TransactionServiceI transactionService;


    @PostMapping("/new")
    public ResponseEntity<Transaction> createNewTransaction(@RequestHeader("Authorization") String auth,
                                                            @RequestBody NewTransactionRequest request
    ) {
        System.out.println(auth);
        System.out.println(request);
        return ResponseEntity.ok(new Transaction());
    }

    @GetMapping("/dashboard")
    public List<TransactionDashboardDto> getTransactionsDashboard(@RequestHeader("Authorization") String auth) {
        return transactionService.getTransactionsDashboard(auth);
    }
}
