package com.example.basicsamplesite.application.qna.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * QnA Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class QnaApplicationDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;

    public QnaApplicationDto(Long id, String title, String content, String author, 
                            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

}
