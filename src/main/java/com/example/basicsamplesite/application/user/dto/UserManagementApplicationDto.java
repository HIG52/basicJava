package com.example.basicsamplesite.application.user.dto;

import lombok.Getter;

import java.time.LocalDate;

/**
 * 회원 관리 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UserManagementApplicationDto {
    private final Long dbId;
    private final String userId;
    private final String username;
    private final String email;
    private final String role;
    private final String phone;
    private final String address;
    private final String zipcode;
    private final String addressDetail;
    private final LocalDate joinDate;

    public UserManagementApplicationDto(Long dbId, String userId, String username, String email, String role, 
                                       String phone, String address, String zipcode, String addressDetail, 
                                       LocalDate joinDate) {
        this.dbId = dbId;
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
