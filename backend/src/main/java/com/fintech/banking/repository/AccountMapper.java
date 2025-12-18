package com.fintech.banking.repository;

import com.fintech.banking.domain.Account;
import com.fintech.banking.domain.Money;

import java.math.BigDecimal;

public final class AccountMapper {

    private AccountMapper() {}

    public static AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance().toBigDecimal(),
                account.getStatus()
        );
    }

    public static Account toDomain(AccountEntity entity) {
        Account account = new Account(
                entity.getId(),
                entity.getAccountNumber()
        );
        account.credit(Money.of(entity.getBalance()));
        return account;
    }
}
