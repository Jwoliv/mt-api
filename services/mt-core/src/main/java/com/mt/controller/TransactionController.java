package com.mt.controller;

import com.mt.model.transaction.Transaction;
import com.mt.request.NewTransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @PostMapping("/new")
    public ResponseEntity<Transaction> createNewTransaction(@RequestHeader("Authorization") String auth,
                                                            @RequestBody NewTransactionRequest request
    ) {
        System.out.println(auth);
        System.out.println(request);
        return ResponseEntity.ok(new Transaction());
    }
}
