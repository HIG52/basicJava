package com.example.basicsamplesite.application.auth.dto;

import lombok.Getter;

/**
 * 사용자 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UserApplicationDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;

    public UserApplicationDto(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
