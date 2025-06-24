package com.example.basicsamplesite.presentation.qna.dto;

import lombok.Getter;

/**
 * QnA 응답 DTO - Presentation Layer
 */
@Getter
public class QnaResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String createdAt;
    
    public QnaResponse(Long id, String title, String content, String author,
                       String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

}
