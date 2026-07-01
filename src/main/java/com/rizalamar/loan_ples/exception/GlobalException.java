package com.rizalamar.loan_ples.exception;

import com.rizalamar.loan_ples.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> handleResponseStatusException(ResponseStatusException e){
        return ResponseEntity
                .status(e.getStatusCode())
                .body(
                        WebResponse.<String>builder()
                                .data(null)
                                .errors(e.getReason())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleGeneralException(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        WebResponse.<String>builder()
                                .data(null)
                                .errors("An unexpected error occured: " + e.getMessage())
                                .build()
                );

    }
}
