package com.example.basicsamplesite.application.qna.command;

import lombok.Getter;

/**
 * QnA 생성 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class CreateQnaCommand {
    private final String title;
    private final String content;

    public CreateQnaCommand(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
