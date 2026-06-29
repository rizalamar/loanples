package com.rizalamar.loan_ples.dto.loan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LoanRequest(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal principalAmount,

        @NotNull(message = "Tenor months is required")
        @Min(1)
        Integer tenorMonths
) {
}
