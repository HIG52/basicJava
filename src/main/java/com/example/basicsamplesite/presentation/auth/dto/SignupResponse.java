package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

/**
 * 회원가입 응답 DTO - Presentation Layer
 */
@Getter
public class SignupResponse {
    private Long dbId;
    private String userId;
    private String username;
    private String email;
    private String role;
    private String phone;
    private String zipcode;
    private String address;
    private String addressDetail;
    
    public SignupResponse(Long dbId, String userId, String username, String email, String role, 
                         String phone, String zipcode, String address, String addressDetail) {
        this.dbId = dbId;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
