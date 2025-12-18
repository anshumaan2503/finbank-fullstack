package com.fintech.banking.repository;

import com.fintech.banking.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository
        extends JpaRepository<TransactionEntity, UUID> {

    Page<TransactionEntity> findByAccountNumber(
            String accountNumber,
            Pageable pageable
    );
}
