package com.fintech.banking.controller;

import com.fintech.banking.dto.TransactionResponse;
import com.fintech.banking.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountNumber}/transactions")
    public Page<TransactionResponse> getTransactions(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return transactionService.getTransactionsByAccountNumber(
                accountNumber,
                page,
                size
        );
    }
}
