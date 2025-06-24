package com.example.basicsamplesite.application.user.command;

import lombok.Getter;

/**
 * 회원 수정 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UpdateUserCommand {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;

    public UpdateUserCommand(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
