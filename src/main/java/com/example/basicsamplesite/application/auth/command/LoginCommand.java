package com.example.basicsamplesite.application.auth.command;

import lombok.Getter;

/**
 * 로그인 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class LoginCommand {
    private final String username; // 실제로는 userId가 전달됨 (스프링 시큐리티 호환성을 위해 username 유지)
    private final String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
