package com.example.basicsamplesite.domain.dto;

import com.example.basicsamplesite.domain.entity.Notice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdAtFormatted,
        Integer viewCount
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static NoticeDetailResponse from(Notice notice) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedAt(),
                notice.getCreatedAt().format(FORMATTER),
                notice.getViewCount()
        );
    }
}
