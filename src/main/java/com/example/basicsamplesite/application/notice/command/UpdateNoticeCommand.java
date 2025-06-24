package com.example.basicsamplesite.application.notice.command;

import lombok.Getter;

/**
 * 공지사항 수정 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UpdateNoticeCommand {
    private final Long id;
    private final String title;
    private final String content;

    public UpdateNoticeCommand(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
