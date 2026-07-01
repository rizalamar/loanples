package com.rizalamar.loan_ples.service;

import com.rizalamar.loan_ples.domain.LoanSchedule;
import com.rizalamar.loan_ples.domain.LoanStatus;
import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.repository.LoanRepository;
import com.rizalamar.loan_ples.repository.LoanScheduleRepository;
import com.rizalamar.loan_ples.repository.UserRepository;
import com.rizalamar.loan_ples.repository.WalletRepository;
import com.rizalamar.loan_ples.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RepaymentService {

    private final LoanScheduleRepository loanScheduleRepository;
    private final ValidationService validationService;

    @Transactional
    public void payInstallment(User borrower, UUID scheduleId){

        LoanSchedule schedule = loanScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan schedule not found"));

        validationService.validateRepayment(borrower, schedule);

        // Kurangi saldo borrower
        borrower.getWallet().setBalance(borrower.getWallet().getBalance().subtract(schedule.getAmountToPay()));

        // Tambah saldo lender
        User lender = schedule.getLoan().getLender();
        lender.getWallet().setBalance(lender.getWallet().getBalance().add(schedule.getAmountToPay()));

        // Update status schecule
        schedule.setPaid(true);

        // Cek penulasan
        boolean allPaid = loanScheduleRepository.findAllByLoan(schedule.getLoan())
                .stream().allMatch(loanSchedule -> loanSchedule.isPaid());

        if(allPaid){
            schedule.getLoan().setStatus(LoanStatus.PAID);
        }
    }
}
