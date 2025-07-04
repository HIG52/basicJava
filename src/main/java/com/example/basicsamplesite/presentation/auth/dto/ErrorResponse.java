package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

/**
 * 에러 응답 DTO - Presentation Layer
 */
@Getter
public class ErrorResponse {
    private final String error;
    
    public ErrorResponse(String error) {
        this.error = error;
    }
}
