package com.example.basicsamplesite.application.auth.command;

import lombok.Getter;

/**
 * 회원가입 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class SignupCommand {
    private final String userId;
    private final String username;
    private final String email;
    private final String password;
    private final String role;
    private final String phone;
    private final String zipcode;
    private final String address;
    private final String addressDetail;

    public SignupCommand(String userId, String username, String email, String password, String role, 
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

    // 사용자 페이지용 생성자 (role 고정)
    public SignupCommand(String userId, String username, String email, String password, 
                        String phone, String zipcode, String address, String addressDetail) {
        this(userId, username, email, password, "user", phone, zipcode, address, addressDetail);
    }
}
