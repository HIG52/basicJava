package com.example.basicsamplesite.application.user.dto;

import lombok.Getter;

import java.time.LocalDate;

/**
 * 회원 관리 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UserManagementApplicationDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;
    private final LocalDate joinDate;

    public UserManagementApplicationDto(Long id, String name, String email, String role, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
    }

}
