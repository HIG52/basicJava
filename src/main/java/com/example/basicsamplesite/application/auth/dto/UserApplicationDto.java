package com.example.basicsamplesite.application.auth.dto;

import lombok.Getter;

/**
 * 사용자 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UserApplicationDto {
    private final Long id;
    private final String userId;
    private final String username;
    private final String email;
    private final String role;

    public UserApplicationDto(Long id, String userId, String username, String email, String role) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
