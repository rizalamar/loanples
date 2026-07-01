package com.rizalamar.loan_ples.service;

import com.rizalamar.loan_ples.domain.Transaction;
import com.rizalamar.loan_ples.domain.TransactionType;
import com.rizalamar.loan_ples.domain.Wallet;
import com.rizalamar.loan_ples.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void createTransaction(Wallet wallet, BigDecimal amount, TransactionType type){
        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .amount(amount)
                .type(type)
                .build();
        transactionRepository.save(transaction);
    }
}
