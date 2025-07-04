package com.example.basicsamplesite.presentation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 메인 페이지 응답을 위한 도메인 계층 DTO
 */
@Getter
@AllArgsConstructor
public class AdminMainResponse {
    private final long totalCount;
    private final long todayCount;
    private final long userCount;


}
