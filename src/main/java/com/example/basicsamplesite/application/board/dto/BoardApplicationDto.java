package com.example.basicsamplesite.application.board.dto;

import com.example.basicsamplesite.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 게시글 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class BoardApplicationDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final Long views;

    public BoardApplicationDto(Long id, String title, String content, String author, 
                              LocalDateTime createdAt, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.views = views;
    }

    public static BoardApplicationDto from(Board board) {
        return new BoardApplicationDto(
            board.getId(),
            board.getTitle(),
            board.getContent(),
            board.getAuthor(),
            board.getCreatedAt(),
            board.getViews()
        );
    }

}
