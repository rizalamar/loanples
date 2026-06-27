package com.rizalamar.loan_ples.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User extends AbstractAuditingEntity {

    @Column(nullable = false)
    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private CreditScore creditScore;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<Loan> borrowedLoans = new ArrayList<>();

    @OneToMany(mappedBy = "lender", cascade = CascadeType.ALL)
    private List<Loan> fundedLoans = new ArrayList<>();

}
