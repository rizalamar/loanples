package com.rizalamar.loan_ples.service;

import com.rizalamar.loan_ples.domain.*;
import com.rizalamar.loan_ples.dto.loan.LoanRequest;
import com.rizalamar.loan_ples.dto.loan.LoanResponse;
import com.rizalamar.loan_ples.mapper.LoanMapper;
import com.rizalamar.loan_ples.repository.LoanRepository;
import com.rizalamar.loan_ples.repository.LoanScheduleRepository;
import com.rizalamar.loan_ples.repository.WalletRepository;
import com.rizalamar.loan_ples.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final WalletRepository walletRepository;
    private final LoanScheduleRepository loanScheduleRepository;
    private final ValidationService validationService;
    private final LoanMapper loanMapper;

    @Transactional
    public LoanResponse createLoan(User user, LoanRequest request){
        validationService.validateLoanCreation(user);

        Loan loan = Loan.builder()
                .borrower(user)
                .principalAmount(request.principalAmount())
                .tenorMonths(request.tenorMonths())
                .interestRate(new BigDecimal("0.05"))
                .status(LoanStatus.PENDING)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        return loanMapper.toResponse(savedLoan);
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> getMarketplace(){
        return loanRepository.findByStatus(LoanStatus.PENDING)
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    @Transactional
    public void fundLoan(User lender, UUID loanId){
        // 1. Ambil data Loan dengan LOCK (supaya tidak diserobot)
        Loan loan = loanRepository.findWithLockById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        // 2. Validasi status
        if(loan.getStatus() != LoanStatus.PENDING){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan is not pending");
        }

        // 3. Ambil wallet & cek saldo
        Wallet lenderWallet = walletRepository.findByUser(lender)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found"));

        if(lenderWallet.getBalance().compareTo(loan.getPrincipalAmount()) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        validationService.validateLoanFunding(lender, loan);

        // 4. Potong saldo
        lenderWallet.setBalance(lender.getWallet().getBalance().subtract(loan.getPrincipalAmount()));
        walletRepository.save(lenderWallet);

        // 5. Update status loan
        loan.setStatus(LoanStatus.DISBURSED);
        loan.setLender(lender);
        loanRepository.save(loan);

        generateSchedules(loan);
    }

    private void generateSchedules(Loan loan){
        BigDecimal principalAmount = loan.getPrincipalAmount();
        BigDecimal interestRate = loan.getInterestRate();
        Integer tenorMonths = loan.getTenorMonths();

        BigDecimal monthlyPrincipal = principalAmount.divide(BigDecimal.valueOf(tenorMonths), 2, RoundingMode.HALF_UP);
        BigDecimal monthlyInterest = principalAmount.multiply(interestRate);
        BigDecimal totalMonthlyPayment = monthlyPrincipal.add(monthlyInterest);

        LocalDate baseDate = LocalDate.now().plusMonths(1).withDayOfMonth(5);

        for(int i = 1; i <= tenorMonths; i++){
            LocalDate dueDate = baseDate.plusMonths(i - 1);
            LocalDateTime dueDateTime = dueDate.atTime(23, 59, 59);

            LoanSchedule schedule = LoanSchedule.builder()
                    .loan(loan)
                    .installmentNumber(i)
                    .amountToPay(totalMonthlyPayment)
                    .dueDate(dueDateTime)
                    .isPaid(false)
                    .build();

            loanScheduleRepository.save(schedule);
        }
    }
}
