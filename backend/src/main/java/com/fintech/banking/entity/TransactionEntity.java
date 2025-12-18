package com.fintech.banking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "balance_after", nullable = false)
    private BigDecimal balanceAfter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    protected TransactionEntity() {
    }

    public TransactionEntity(
            String accountNumber,
            BigDecimal amount,
            BigDecimal balanceAfter,
            TransactionType type
    ) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
