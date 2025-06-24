package com.example.basicsamplesite.application.auth.command;

import lombok.Getter;

/**
 * 회원가입 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class SignupCommand {
    private final String name;
    private final String email;
    private final String password;

    public SignupCommand(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
