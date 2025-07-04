package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

/**
 * 로그인 응답 DTO - Presentation Layer
 */
@Getter
public class LoginResponse {
    private final String userId;
    private final String role;
    
    public LoginResponse(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}
