package com.example.basicsamplesite.application.notice.command;

import lombok.Getter;

/**
 * 공지사항 생성 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class CreateNoticeCommand {
    private final String title;
    private final String content;

    public CreateNoticeCommand(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
