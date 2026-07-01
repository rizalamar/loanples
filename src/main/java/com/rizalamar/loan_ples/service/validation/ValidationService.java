package com.rizalamar.loan_ples.service.validation;

import com.rizalamar.loan_ples.domain.LoanSchedule;
import com.rizalamar.loan_ples.domain.LoanStatus;
import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ValidationService {

    private final LoanRepository loanRepository;

    public void validateLoanCreation(User borrower){
        if(loanRepository.existsByBorrowerAndStatus(borrower, LoanStatus.PENDING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have a pending loan request");
        }
    }

    public void validateRepayment(User borrower, LoanSchedule schedule){
        if(!schedule.getLoan().getBorrower().getId().equals(borrower.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your loan");
        }

        if(schedule.isPaid()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already paid");
        }

        if(borrower.getWallet().getBalance().compareTo(schedule.getAmountToPay()) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }
    }

}
