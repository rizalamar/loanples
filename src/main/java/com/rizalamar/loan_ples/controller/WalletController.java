package com.rizalamar.loan_ples.controller;

import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.dto.WebResponse;
import com.rizalamar.loan_ples.dto.wallet.WalletResponse;
import com.rizalamar.loan_ples.dto.wallet.WalletTransactionRequest;
import com.rizalamar.loan_ples.security.annotation.CurrentUser;
import com.rizalamar.loan_ples.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance")
    @PreAuthorize("hasAnyAuthority('ROLE_BORROWER', 'ROLE_LENDER')")
    public ResponseEntity<WebResponse<WalletResponse>> getBalance(@CurrentUser User user){
        WalletResponse walletResponse = walletService.getBalance(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<WalletResponse>builder()
                                .data(walletResponse)
                                .errors(null)
                                .build()
                );
    }

    @PostMapping("/top-up")
    @PreAuthorize("hasAnyAuthority('ROLE_BORROWER', 'ROLE_LENDER')")
    public ResponseEntity<WebResponse<WalletResponse>> topUp(
            @CurrentUser User user,
            @Valid @RequestBody WalletTransactionRequest request
    ){
        WalletResponse walletResponse = walletService.topUp(user, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<WalletResponse>builder()
                                .data(walletResponse)
                                .errors(null)
                                .build()
                );
    }

}
