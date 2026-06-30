package com.rizalamar.loan_ples.repository;

import com.rizalamar.loan_ples.domain.Loan;
import com.rizalamar.loan_ples.domain.LoanStatus;
import com.rizalamar.loan_ples.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    boolean existsByBorrowerAndStatus(User borrower, LoanStatus status);

    List<Loan> findByStatus(LoanStatus status);
}
