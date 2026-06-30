package com.rizalamar.loan_ples.service;

import com.rizalamar.loan_ples.domain.Loan;
import com.rizalamar.loan_ples.domain.LoanStatus;
import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.dto.loan.LoanRequest;
import com.rizalamar.loan_ples.dto.loan.LoanResponse;
import com.rizalamar.loan_ples.mapper.LoanMapper;
import com.rizalamar.loan_ples.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final ValidationService validationService;
    private final LoanMapper loanMapper;

    @Transactional
    public LoanResponse createLoan(User user, LoanRequest request){
        validationService.validateLoanCreation(user);

        Loan loan = Loan.builder()
                .borrower(user)
                .principalAmount(request.principalAmount())
                .tenorMonths(request.tenorMonths())
                .interestRate(new BigDecimal("0.0.5"))
                .status(LoanStatus.PENDING)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        return loanMapper.toResponse(savedLoan);
    }
}
