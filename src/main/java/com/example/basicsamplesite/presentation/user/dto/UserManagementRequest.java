package com.example.basicsamplesite.presentation.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원 관리 요청 DTO - Presentation Layer
 */
public class UserManagementRequest {
    
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
    @Pattern(regexp = "^(user|admin)$", message = "역할은 user 또는 admin이어야 합니다")
    private String role;
    
    // 선택 필드들
    private String phone;
    private String address;
    private String zipcode;
    private String addressDetail;
    
    // Default constructor for JSON deserialization
    public UserManagementRequest() {
    }
    
    public UserManagementRequest(String userId, String username, String email, String password, String role, 
                                String phone, String address, String zipcode, String addressDetail) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.addressDetail = addressDetail;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public String getAddressDetail() {
        return addressDetail;
    }
    
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
