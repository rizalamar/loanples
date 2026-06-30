package com.rizalamar.loan_ples.mapper;

import com.rizalamar.loan_ples.domain.Wallet;
import com.rizalamar.loan_ples.dto.wallet.WalletResponse;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public WalletResponse toResponse(Wallet wallet){
        return WalletResponse.builder()
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();
    }
}
