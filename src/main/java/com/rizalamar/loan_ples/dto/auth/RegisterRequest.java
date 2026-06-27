package com.rizalamar.loan_ples.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be at least 3 characters and maximum 50 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password at least has 6 characters")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d).*$",
                message = "Password must contains 1 capital and 1 number"
        )
        String password
) {
}
