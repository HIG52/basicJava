package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 로그인 요청 DTO - Presentation Layer
 */
@Getter
public class LoginRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다")
    @Size(min = 4, max = 20, message = "사용자 ID는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 영문과 숫자만 사용 가능합니다")
    private String userId;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    // Default constructor for JSON deserialization
    public LoginRequest() {
    }
    
    public LoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
