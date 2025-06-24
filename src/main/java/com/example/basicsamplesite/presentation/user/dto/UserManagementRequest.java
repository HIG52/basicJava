package com.example.basicsamplesite.presentation.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원 관리 요청 DTO - Presentation Layer
 */
public class UserManagementRequest {
    
    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 50, message = "이름은 2~50자 사이여야 합니다")
    private String name;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    @NotBlank(message = "역할은 필수입니다")
    @Pattern(regexp = "^(user|admin)$", message = "역할은 user 또는 admin이어야 합니다")
    private String role;
    
    // Default constructor for JSON deserialization
    public UserManagementRequest() {
    }
    
    public UserManagementRequest(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
