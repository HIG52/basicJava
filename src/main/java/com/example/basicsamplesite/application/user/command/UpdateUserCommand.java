package com.example.basicsamplesite.application.user.command;

import lombok.Getter;

/**
 * 회원 수정 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UpdateUserCommand {
    private final Long dbId;
    private final String userId;
    private final String username;
    private final String email;
    private final String password;
    private final String role;
    private final String phone;
    private final String address;
    private final String zipcode;
    private final String addressDetail;

    public UpdateUserCommand(Long dbId, String userId, String username, String email, String password, String role, 
                            String phone, String address, String zipcode, String addressDetail) {
        this.dbId = dbId;
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

}
