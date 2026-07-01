package com.rizalamar.loan_ples.repository;

import com.rizalamar.loan_ples.domain.Loan;
import com.rizalamar.loan_ples.domain.LoanSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanScheduleRepository extends JpaRepository<LoanSchedule, UUID> {

    Optional<LoanSchedule> findById(UUID scheduleId);

    List<LoanSchedule> findAllByLoan(Loan loan);
}
