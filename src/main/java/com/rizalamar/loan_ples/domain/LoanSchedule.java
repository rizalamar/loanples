package com.rizalamar.loan_ples.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "loan_schedules")
public class LoanSchedule extends AbstractAuditingEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", referencedColumnName = "id", nullable = false)
    private Loan loan;

    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @Column(name = "amount_to_pay", nullable = false)
    private BigDecimal amountToPay;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Builder.Default
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = false;
}
