package com.example.basicsamplesite.presentation.dto;

import com.example.basicsamplesite.domain.dto.MainResponse;

/**
 * 메인 페이지 응답을 클라이언트에 전달하기 위한 DTO
 */
public record MainResponseDto(
        String message,
        String timestamp
) {
    /**
     * 도메인 계층의 DTO를 presentation 계층의 DTO로 변환
     */
    public static MainResponseDto from(MainResponse domain) {
        return new MainResponseDto(
                domain.message(),
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
