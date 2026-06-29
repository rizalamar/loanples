package com.rizalamar.loan_ples.controller;

import com.rizalamar.loan_ples.dto.WebResponse;
import com.rizalamar.loan_ples.dto.auth.AuthResponse;
import com.rizalamar.loan_ples.dto.auth.LoginRequest;
import com.rizalamar.loan_ples.dto.auth.RegisterRequest;
import com.rizalamar.loan_ples.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<AuthResponse>> register(@Valid  @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<AuthResponse>builder()
                                .data(response)
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<AuthResponse>builder()
                                .data(response)
                                .build()
                );
    }
}
