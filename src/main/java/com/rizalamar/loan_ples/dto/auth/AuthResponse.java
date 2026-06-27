package com.rizalamar.loan_ples.dto.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthResponse(
        String token,
        UUID userId,
        String email,
        String role
) {
}
