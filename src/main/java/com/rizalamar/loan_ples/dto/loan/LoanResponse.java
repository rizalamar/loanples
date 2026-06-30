package com.rizalamar.loan_ples.dto.loan;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record LoanResponse(
        UUID id,
        UUID borrowerId,
        UUID lenderId,
        BigDecimal pincipalAmount,
        Integer tenorMonths,
        BigDecimal interestRate,
        String status
) {
}
