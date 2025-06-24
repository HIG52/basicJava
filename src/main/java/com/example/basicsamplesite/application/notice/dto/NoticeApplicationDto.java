package com.example.basicsamplesite.application.notice.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 공지사항 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class NoticeApplicationDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final int viewCount;

    public NoticeApplicationDto(Long id, String title, String content, String author, LocalDateTime createdAt, int viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }

}
