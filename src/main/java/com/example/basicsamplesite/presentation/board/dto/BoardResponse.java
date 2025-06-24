package com.example.basicsamplesite.presentation.board.dto;

import lombok.Getter;

/**
 * 게시글 응답 DTO - Presentation Layer
 */
@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private Long views;
    
    public BoardResponse(Long id, String title, String content, String author, 
                        String createdAt, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.views = views;
    }

}
