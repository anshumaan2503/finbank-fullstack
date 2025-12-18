package com.fintech.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
