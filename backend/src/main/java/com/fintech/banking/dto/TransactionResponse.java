package com.fintech.banking.dto;

import com.fintech.banking.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String accountNumber,
        BigDecimal amount,
        BigDecimal balanceAfter,
        TransactionType type,
        LocalDateTime timestamp
) {
}
