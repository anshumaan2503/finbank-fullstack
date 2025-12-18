package com.fintech.banking.controller;

import com.fintech.banking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 🔹 CREDIT
    @PostMapping("/{accountNumber}/credit")
    public ResponseEntity<Void> credit(
            @PathVariable String accountNumber,
            @RequestBody AmountRequest request
    ) {
        accountService.credit(accountNumber, request.amount());
        return ResponseEntity.ok().build();
    }

    // 🔹 DEBIT
    @PostMapping("/{accountNumber}/debit")
    public ResponseEntity<Void> debit(
            @PathVariable String accountNumber,
            @RequestBody AmountRequest request
    ) {
        accountService.debit(accountNumber, request.amount());
        return ResponseEntity.ok().build();
    }

    // 🔹 GET ACCOUNT DETAILS
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable String accountNumber
    ) {
        var account = accountService.getAccount(accountNumber);

        return ResponseEntity.ok(
                new AccountResponse(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getBalance().toBigDecimal(),
                        account.getStatus().name()
                )
        );
    }

    // 🔹 REQUEST DTO
    public record AmountRequest(BigDecimal amount) {}

    // 🔹 RESPONSE DTO
    public record AccountResponse(
            UUID id,
            String accountNumber,
            BigDecimal balance,
            String status
    ) {}
}
