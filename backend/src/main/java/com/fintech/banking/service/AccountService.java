package com.fintech.banking.service;

import com.fintech.banking.domain.Account;
import com.fintech.banking.domain.Money;
import com.fintech.banking.exception.AccountNotFoundException;
import com.fintech.banking.repository.AccountEntity;
import com.fintech.banking.repository.AccountMapper;
import com.fintech.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // CREATE
    @Transactional
    public Account createAccount(String accountNumber) {
        Account account = new Account(UUID.randomUUID(), accountNumber);
        AccountEntity entity = accountRepository.save(AccountMapper.toEntity(account));
        return AccountMapper.toDomain(entity);
    }

    // CREDIT
    @Transactional
    public void credit(String accountNumber, BigDecimal amount) {
        AccountEntity entity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        Account account = AccountMapper.toDomain(entity);
        account.credit(Money.of(amount));

        accountRepository.save(AccountMapper.toEntity(account));
    }

    // DEBIT
    @Transactional
    public void debit(String accountNumber, BigDecimal amount) {
        AccountEntity entity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        Account account = AccountMapper.toDomain(entity);
        account.debit(Money.of(amount));

        accountRepository.save(AccountMapper.toEntity(account));
    }

    // ✅ GET ACCOUNT (THIS WAS MISSING)
    @Transactional(readOnly = true)
    public Account getAccount(String accountNumber) {
        AccountEntity entity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        return AccountMapper.toDomain(entity);
    
    }
    
}
