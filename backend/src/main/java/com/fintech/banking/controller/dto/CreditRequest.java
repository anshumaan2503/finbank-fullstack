package com.fintech.banking.controller.dto;

import java.math.BigDecimal;

public class CreditRequest {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
