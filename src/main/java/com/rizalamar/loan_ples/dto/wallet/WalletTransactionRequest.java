package com.rizalamar.loan_ples.dto.wallet;

import java.math.BigDecimal;

public record WalletTransactionRequest(
        BigDecimal amount
) {
}
