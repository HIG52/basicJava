package com.example.basicsamplesite.application.auth.dto;

import lombok.Getter;

/**
 * 인증 결과 Application DTO - Application Layer에서 Presentation Layer로 전달되는 DTO
 */
@Getter
public class AuthApplicationDto {
    private final String token;
    private final UserApplicationDto user;

    public AuthApplicationDto(String token, UserApplicationDto user) {
        this.token = token;
        this.user = user;
    }

}
