package com.example.basicsamplesite.application.auth.command;

import lombok.Getter;

/**
 * 로그인 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class LoginCommand {
    private final String email;
    private final String password;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
