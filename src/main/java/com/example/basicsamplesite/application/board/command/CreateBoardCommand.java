package com.example.basicsamplesite.application.board.command;

import lombok.Getter;

/**
 * 게시글 생성 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class CreateBoardCommand {
    private final String title;
    private final String content;

    public CreateBoardCommand(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
