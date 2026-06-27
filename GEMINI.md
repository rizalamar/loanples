# Peer-to-Peer (P2P) Loan Experiment System (PLES) - Project Instructions

## Project Summary
A secure and highly structured Peer-to-Peer (P2P) Lending simulation system. The platform bridges individual borrowers and lenders directly, featuring automated amortization scheduling, flat platform fee deductions, and a foundation for future credit scoring mechanics. This project is strictly for educational and logical experimentation.

## Core Tech Stack
- **Language:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x
- **Security:** Spring Security & JWT (Roles: BORROWER, LENDER)
- **Database:** Spring Data JPA / Hibernate (PostgreSQL) with JPA Auditing
- **Documentation:** Springdoc OpenAPI (Swagger)

## Architectural Mandates
1. *Entity Design:*
    - All domain entities must extend an `AbstractAuditingEntity` (capturing created_by, created_date, etc.).
    - Use Lombok's `@Getter` and `@Setter` instead of `@Data`.
    - Primary Keys must be **UUID**.
    - Use `BigDecimal` for all monetary amounts to ensure financial calculation precision.
    - Use **Enums** for Loan Status: `PENDING`, `DISBURSED`, `PAID`, `OVERDUE`.

2. *Flexible Loan & Amortization Logic (Fase 1):*
    - Model: 1 Loan Request is created by a Borrower and funded by exactly 1 Lender (No crowdfunding in Phase 1).
    - Platform Fee: A flat **0.2%** is deducted automatically from the principal amount during disbursement.
    - Interest Rate: Set as a fixed flat rate (e.g., 5% monthly) for Phase 1.
    - Upon funding by a Lender, the system must automatically generate rows of amortization schedules (`LoanSchedule`) split by monthly installments based on the requested tenor.

3. *Security & Access:*
    - Public / Guest: Register and Login.
    - Borrower: Create Loan Requests (Creates a `PENDING` Loan), View Own Loans & Schedules, Pay Monthly Bills.
    - Lender: Browse Pending Loans in Marketplace, Top Up Wallet, Fund Loans (Updates `lender_id` and generates schedules).
    - Implement `CustomAccessDeniedHandler` and `CustomAuthenticationEntryPoint` for uniform JSON error responses.

4. *Data Transfer (DTOs):*
    - Use Java 21 **Records** for all API Requests and Responses.
    - Wrap all API responses in a consistent `WebResponse<T>` envelope.

5. *Validation:*
    - Use `@Valid` in Controllers for input validation.
    - Use a dedicated **`ValidationService`** for business rules (e.g., verifying wallet balance before funding, preventing double funding on the same loan).

## Naming Conventions
- Packages: `com.rizalamar.loanples`
- Sub-packages: `domain`, `repository`, `service`, `controller`, `dto`, `security`, `config`, `exception`.
- Database Tables: snake_case.
- Variables & Methods: camelCase.

## Key Domains
- **User:** (Id, Name, Email, Password, Role Enum [BORROWER, LENDER], Reputasi/CreditScore Enum)
- **Wallet:** (Id, User UUID [One-to-One], Balance `BigDecimal`)
- **Loan:** (Id, Borrower UUID [The Creator], Lender UUID [The Funder, null initially], PrincipalAmount, TenorMonths, InterestRate, Status Enum)
- **LoanSchedule:** (Id, Loan UUID, InstallmentNumber [e.g., Month 1, 2, 3], AmountToPay `BigDecimal`, DueDate `LocalDateTime`, IsPaid `Boolean`)
- **Transaction:** (Id, Wallet UUID, Amount, Type Enum [TOPUP, WITHDRAW, FUND, REPAYMENT, PLATFORM_FEE], Timestamp)

---

## Future Roadmap (Phase 2)
- **Dynamic Credit Scoring (FICO-style System):**
    - Implement a numeric credit score tracking system ranging from **300 to 850** stored within the `User` domain.
    - **Starting Baseline:** New users register with a default baseline score of **600 (Neutral)**.
    - **Gamified Reward Engine:** Successfully paying a monthly installment on or before the `due_date` grants a **+15 points** reward.
    - **Punishment & Penalty Engine:**
        - Missing the deadline by 1 day triggers an immediate **-30 points** penalty and moves the loan status to `OVERDUE`.
        - Remaining unpaid after 7 days triggers a severe **-100 points** penalty.
    - **Risk-Based Progression:** Future interest rates and loan amount thresholds will dynamically scale up or down based on the user's current numeric score.
- **Crowdfunding (Patungan):** Allow multiple Lenders to co-fund a single Loan Request.