package com.example.basicsamplesite.presentation.user.dto;

import lombok.Getter;

/**
 * 회원 관리 응답 DTO - Presentation Layer
 */
@Getter
public class UserManagementResponse {
    private final Long id;
    private final String userId;
    private final String username;
    private final String email;
    private final String role;
    private final String phone;
    private final String address;
    private final String zipcode;
    private final String addressDetail;
    private final String joinDate;
    
    public UserManagementResponse(Long id, String userId, String username, String email, String role,
                                  String phone, String address, String zipcode, String addressDetail,
                                  String joinDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.addressDetail = addressDetail;
        this.joinDate = joinDate;
    }

}
