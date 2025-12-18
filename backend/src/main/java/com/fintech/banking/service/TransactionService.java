package com.fintech.banking.service;

import com.fintech.banking.dto.TransactionResponse;
import com.fintech.banking.entity.TransactionEntity;
import com.fintech.banking.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<TransactionResponse> getTransactionsByAccountNumber(
            String accountNumber,
            int page,
            int size
    ) {
        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by("timestamp").descending()
        );

        return transactionRepository
                .findByAccountNumber(accountNumber, pageable)
                .map(this::toResponse);
    }

    private TransactionResponse toResponse(TransactionEntity entity) {
        return new TransactionResponse(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getAmount(),
                entity.getBalanceAfter(),
                entity.getType(),
                entity.getTimestamp()
        );
    }
}
