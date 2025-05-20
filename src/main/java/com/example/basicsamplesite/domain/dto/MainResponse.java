package com.example.basicsamplesite.domain.dto;

/**
 * 메인 페이지 응답을 위한 도메인 계층 DTO
 */
public record MainResponse(
        String message
) {
    /**
     * 메시지로 응답 객체 생성
     */
    public static MainResponse of(String message) {
        return new MainResponse(message);
    }
}
