package com.example.basicsamplesite.presentation.dto;

import com.example.basicsamplesite.domain.entity.Notice;

import java.time.format.DateTimeFormatter;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        String category,
        String createdAt,
        Integer viewCount
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCategory(),
                notice.getCreatedAt().format(FORMATTER),
                notice.getViewCount()
        );
    }
}
