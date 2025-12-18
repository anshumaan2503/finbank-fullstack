package com.fintech.banking.repository;

import com.fintech.banking.domain.AccountStatus;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    protected AccountEntity() {
        // JPA only
    }

    public AccountEntity(UUID id, String accountNumber, BigDecimal balance, AccountStatus status) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
