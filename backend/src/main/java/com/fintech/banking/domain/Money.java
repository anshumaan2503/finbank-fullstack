package com.fintech.banking.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money {

    private final BigDecimal value;

    private Money(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Money value cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money value cannot be negative");
        }
        this.value = value;
    }

    // Factory method
    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    // Zero money
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    // Add money
    public Money add(Money other) {
        return new Money(this.value.add(other.value));
    }

    // Subtract money
    public Money subtract(Money other) {
        BigDecimal result = this.value.subtract(other.value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        return new Money(result);
    }

    // Comparison
    public boolean isLessThan(Money other) {
        return this.value.compareTo(other.value) < 0;
    }

    // For persistence / API
    public BigDecimal toBigDecimal() {
        return value;
    }

    // Equals & hashCode (important for domain)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return value.compareTo(money.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}
