package com.mt.controller;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.request.ChangeTransactionRequest;
import com.mt.request.UpdatedTransactionRequest;
import com.mt.response.PageElementsResponse;
import com.mt.service.TransactionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Setter(onMethod = @__({@Autowired}))
    private TransactionService transactionService;


    @GetMapping
    public PageElementsResponse<TransactionDashboardDto> getTransactionsPageable(@RequestHeader("Authorization") String auth,
                                                                                 @RequestParam Integer pageNumber,
                                                                                 @RequestParam Integer pageSize
    ) {
        return transactionService.getTransactionsPageable(auth, pageNumber, pageSize);
    }

    @PostMapping("/new")
    public CreatedTransaction createNewTransaction(@RequestHeader("Authorization") String auth,
                                                   @RequestBody ChangeTransactionRequest request
    ) {
        return transactionService.createNewTransaction(auth, request);
    }

    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        return transactionService.getUserTransactionById(auth, id);
    }

    @PatchMapping("/{id}")
    public TransactionDto updateTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id,
                                                @RequestBody UpdatedTransactionRequest transaction) {
        return transactionService.updateTransactionById(auth, id, transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionById(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        transactionService.deleteTransactionById(auth, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dashboard")
    public List<TransactionDashboardDto> getTransactionsDashboard(@RequestHeader("Authorization") String auth) {
        return transactionService.getTransactionsDashboard(auth);
    }

    @GetMapping("/account/{id}")
    public PageElementsResponse<TransactionDashboardDto> getTransactionByAccountId(@RequestHeader("Authorization") String auth,
                                                                                   @PathVariable("id") Long id,
                                                                                   @RequestParam Integer pageNumber,
                                                                                   @RequestParam Integer pageSize) {
        return transactionService.getTransactionByAccountId(auth, id, pageNumber, pageSize);
    }
}
