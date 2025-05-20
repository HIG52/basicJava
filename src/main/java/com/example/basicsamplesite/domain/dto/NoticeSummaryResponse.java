package com.example.basicsamplesite.domain.dto;

import com.example.basicsamplesite.domain.entity.Notice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record NoticeSummaryResponse(
        Long id,
        String title,
        LocalDateTime createdAt,
        String createdAtFormatted,
        Integer viewCount
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static NoticeSummaryResponse from(Notice notice) {
        return new NoticeSummaryResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getCreatedAt(),
                notice.getCreatedAt().format(FORMATTER),
                notice.getViewCount()
        );
    }
    
    public static List<NoticeSummaryResponse> listFrom(List<Notice> notices) {
        return notices.stream()
                .map(NoticeSummaryResponse::from)
                .collect(Collectors.toList());
    }
}
