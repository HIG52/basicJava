package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

/**
 * 회원가입 응답 DTO - Presentation Layer
 */
@Getter
public class SignupResponse {
    private Long id;
    private String name;
    private String email;
    
    public SignupResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
