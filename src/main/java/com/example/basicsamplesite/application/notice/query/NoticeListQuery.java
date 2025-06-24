package com.example.basicsamplesite.application.notice.query;

import lombok.Getter;

/**
 * 공지사항 목록 조회 쿼리 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class NoticeListQuery {
    private final int page;
    private final int size;
    private final String search;

    public NoticeListQuery(int page, int size, String search) {
        this.page = page;
        this.size = size;
        this.search = search;
    }

}
