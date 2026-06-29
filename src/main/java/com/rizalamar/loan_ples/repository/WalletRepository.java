package com.rizalamar.loan_ples.repository;

import com.rizalamar.loan_ples.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Wallet findByUserEmail(String email);
}
