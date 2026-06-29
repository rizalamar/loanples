package com.rizalamar.loan_ples.service;

import com.rizalamar.loan_ples.domain.User;
import com.rizalamar.loan_ples.domain.Wallet;
import com.rizalamar.loan_ples.dto.wallet.WalletResponse;
import com.rizalamar.loan_ples.dto.wallet.WalletTransactionRequest;
import com.rizalamar.loan_ples.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public WalletResponse getBalance(User user){
        Wallet wallet = walletRepository.findByUserEmail(user.getEmail());

        return WalletResponse.builder()
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();
    }

    @Transactional
    public WalletResponse topUp(User user, WalletTransactionRequest request){
        Wallet wallet = walletRepository.findByUserEmail(user.getEmail());

        wallet.setBalance(wallet.getBalance().add(request.amount()));
        walletRepository.save(wallet);

        return WalletResponse.builder()
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();
    }

    @Transactional
    public WalletResponse withdraw(User user, WalletTransactionRequest request){
        Wallet wallet = walletRepository.findByUserEmail(user.getEmail());

        if(wallet.getBalance().compareTo(request.amount()) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(request.amount()));
        walletRepository.save(wallet);

        return WalletResponse.builder()
                .userId(user.getId())
                .balance(wallet.getBalance())
                .build();
    }
}
