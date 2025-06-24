package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 로그인 요청 DTO - Presentation Layer
 */
@Getter
public class LoginRequest {
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    // Default constructor for JSON deserialization
    public LoginRequest() {
    }
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
