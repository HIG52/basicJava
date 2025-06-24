package com.example.basicsamplesite.application.user.command;

import lombok.Getter;

/**
 * 회원 생성 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class CreateUserCommand {
    private final String name;
    private final String email;
    private final String role;

    public CreateUserCommand(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
