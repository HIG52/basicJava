package com.example.basicsamplesite.application.qna.command;

import lombok.Getter;

/**
 * QnA 수정 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UpdateQnaCommand {
    private final Long id;
    private final String title;
    private final String content;

    public UpdateQnaCommand(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
