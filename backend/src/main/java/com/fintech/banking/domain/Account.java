package com.fintech.banking.domain;

import java.util.UUID;

public class Account {

    private final UUID id;
    private final String accountNumber;
    private Money balance;
    private AccountStatus status;

    public Account(UUID id, String accountNumber) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = Money.zero();
        this.status = AccountStatus.ACTIVE;
    }

    // Credit money
    public void credit(Money amount) {
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        this.balance = this.balance.add(amount);
    }

    // Debit money
    public void debit(Money amount) {
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if (this.balance.isLessThan(amount)) {
            throw new IllegalStateException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Money getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
