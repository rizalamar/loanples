package com.rizalamar.loan_ples.controller;

import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.dto.WebResponse;
import com.rizalamar.loan_ples.dto.loan.LoanRequest;
import com.rizalamar.loan_ples.dto.loan.LoanResponse;
import com.rizalamar.loan_ples.security.annotation.CurrentUser;
import com.rizalamar.loan_ples.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/create-loan")
    @PreAuthorize("hasAnyAuthority('ROLE_BORROWER')")
    public ResponseEntity<WebResponse<LoanResponse>> createLoan(
            @CurrentUser User user,
            @Valid @RequestBody LoanRequest request
            ) {
        LoanResponse loanResponse = loanService.createLoan(user, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<LoanResponse>builder()
                                .data(loanResponse)
                                .errors(null)
                                .build()
                );
    }

    @GetMapping("/marketplace")
    @PreAuthorize("hasAnyAuthority('ROLE_LENDER')")
    public ResponseEntity<WebResponse<List<LoanResponse>>> getMarketplace(){
        List<LoanResponse> loanResponseList = loanService.getMarketplace();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<List<LoanResponse>>builder()
                                .data(loanResponseList)
                                .errors(null)
                                .build()
                );
    }

    @PostMapping("/{loanId}/fund")
    @PreAuthorize("hasAnyAuthority('ROLE_LENDER')")
    public ResponseEntity<WebResponse<String>> fundLoan(
            @CurrentUser User lender,
            @PathVariable UUID loanId
            ) {
        loanService.fundLoan(lender, loanId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<String>builder()
                                .data("Loan funded successfully")
                                .errors(null)
                                .build()
                );
    }
}
