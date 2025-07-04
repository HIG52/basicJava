package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

/**
 * 사용자 정보 응답 DTO - Presentation Layer
 */
@Getter
public class UserInfoResponse {
    private final String userId;
    private final String role;
    
    public UserInfoResponse(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}
