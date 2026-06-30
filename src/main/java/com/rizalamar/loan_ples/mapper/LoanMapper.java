package com.rizalamar.loan_ples.mapper;

import com.rizalamar.loan_ples.domain.Loan;
import com.rizalamar.loan_ples.dto.loan.LoanResponse;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    public LoanResponse toResponse(Loan loan){
        return LoanResponse.builder()
                .id(loan.getId())
                .borrowerId(loan.getBorrower().getId())
                .lenderId(loan.getLender() != null ? loan.getLender().getId() : null)
                .pincipalAmount(loan.getPrincipalAmount())
                .tenorMonths(loan.getTenorMonths())
                .interestRate(loan.getInterestRate())
                .status(loan.getStatus().name())
                .build();
    }
}
