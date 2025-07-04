package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 관리자용 회원가입 요청 DTO - Presentation Layer
 */
@Getter
public class AdminSignupRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다")
    @Size(min = 4, max = 20, message = "사용자 ID는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 영문과 숫자만 사용 가능합니다")
    private String userId;
    
    @NotBlank(message = "사용자명은 필수입니다")
    @Size(min = 2, max = 50, message = "사용자명은 2~50자 사이여야 합니다")
    private String username;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    private String password;
    
    @NotBlank(message = "역할은 필수입니다")
    @Pattern(regexp = "^(user|admin)$", message = "역할은 'user' 또는 'admin'만 가능합니다")
    private String role;
    
    // 선택 필드들
    private String phone;
    private String zipcode;
    private String address;
    private String addressDetail;
    
    // Default constructor for JSON deserialization
    public AdminSignupRequest() {
    }
    
    public AdminSignupRequest(String userId, String username, String email, String password, String role, 
                             String phone, String zipcode, String address, String addressDetail) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
