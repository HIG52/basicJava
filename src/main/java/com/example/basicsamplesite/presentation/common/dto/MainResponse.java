package com.example.basicsamplesite.presentation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 메인 페이지 응답을 위한 도메인 계층 DTO
 */
@Getter
@AllArgsConstructor
public class MainResponse {
    private final String message;

    /**
     * 메시지로 응답 객체 생성
     */
    public static MainResponse of(String message) {
        return new MainResponse(message);
    }
}
