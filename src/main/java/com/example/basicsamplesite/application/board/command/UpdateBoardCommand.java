package com.example.basicsamplesite.application.board.command;

import lombok.Getter;

/**
 * 게시글 수정 커맨드 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UpdateBoardCommand {
    private final Long id;
    private final String title;
    private final String content;

    public UpdateBoardCommand(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
