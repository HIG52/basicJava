package com.example.basicsamplesite.presentation.notice.dto;

import lombok.Getter;

/**
 * 공지사항 응답 DTO - Presentation Layer
 */
@Getter
public class NoticeResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String createdAt;
    private final int viewCount;
    
    public NoticeResponse(Long id, String title, String content, String author, String createdAt, int viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }

}
