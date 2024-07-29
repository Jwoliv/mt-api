package com.mt.controller;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.node.TransactionService;
import com.mt.request.NewTransactionRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/middle/transaction")
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

    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        return transactionService.getUserTransactionById(auth, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        transactionService.deleteTransactionById(auth, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/{id}")
    public List<TransactionDashboardDto> getTransactionByAccountId(@RequestHeader("Authorization") String auth,
                                                                   @PathVariable("id") Long id,
                                                                   @RequestParam Integer pageNumber,
                                                                   @RequestParam Integer pageSize) {
        return transactionService.getTransactionByAccountId(auth, id, pageNumber, pageSize);
    }

}
