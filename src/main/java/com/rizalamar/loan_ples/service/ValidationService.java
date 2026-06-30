package com.rizalamar.loan_ples.service;

import com.rizalamar.loan_ples.domain.LoanStatus;
import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class ValidationService {

    private final LoanRepository loanRepository;

    public void validateLoanCreation(User borrower){
        if(loanRepository.existsByBorrowerAndStatus(borrower, LoanStatus.PENDING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have a pending loan request");
        }
    }

}
