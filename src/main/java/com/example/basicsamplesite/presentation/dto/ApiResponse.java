package com.example.basicsamplesite.presentation.dto;

import java.time.LocalDateTime;

/**
 * API 응답을 위한 공통 포맷 DTO
 * @param <T> 응답 데이터 타입
 */
public record ApiResponse<T>(
        T data,
        String message,
        LocalDateTime timestamp,
        boolean success
) {
    /**
     * 성공 응답 생성
     * @param data 응답 데이터
     * @param <T> 데이터 타입
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "요청이 성공적으로 처리되었습니다.", LocalDateTime.now(), true);
    }

    /**
     * 성공 응답 생성 (메시지 포함)
     * @param data 응답 데이터
     * @param message 응답 메시지
     * @param <T> 데이터 타입
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, LocalDateTime.now(), true);
    }

    /**
     * 실패 응답 생성
     * @param message 오류 메시지
     * @param <T> 데이터 타입
     * @return 실패 응답 객체
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, LocalDateTime.now(), false);
    }
}
