package com.rizalamar.loan_ples.controller;

import com.rizalamar.loan_ples.domain.LoanSchedule;
import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.dto.WebResponse;
import com.rizalamar.loan_ples.security.annotation.CurrentUser;
import com.rizalamar.loan_ples.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class RepaymentController {

    private final RepaymentService repaymentService;

    @PostMapping("/${scheduleId}")
    public ResponseEntity<WebResponse<String>> payInstallment(
            @CurrentUser User borrower,
            @PathVariable UUID scheduleId
            ) {
        repaymentService.payInstallment(borrower, scheduleId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<String>builder()
                                .data("Payment success")
                                .build()
                );
    }
}
