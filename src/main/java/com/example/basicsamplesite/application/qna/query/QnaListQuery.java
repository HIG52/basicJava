package com.example.basicsamplesite.application.qna.query;

import lombok.Getter;

/**
 * QnA 목록 조회 쿼리 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class QnaListQuery {
    private final int page;
    private final int size;
    private final String search;

    public QnaListQuery(int page, int size, String search) {
        this.page = page;
        this.size = size;
        this.search = search;
    }

}
