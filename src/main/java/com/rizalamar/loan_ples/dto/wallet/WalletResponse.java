package com.rizalamar.loan_ples.dto.wallet;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record WalletResponse(
        UUID userId,
        BigDecimal balance
) {
}
