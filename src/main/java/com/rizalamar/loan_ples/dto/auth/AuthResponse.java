package com.rizalamar.loan_ples.dto.auth;

import java.util.UUID;

public record AuthResponse(
        String token,
        UUID userId,
        String email,
        String role
) {
}
