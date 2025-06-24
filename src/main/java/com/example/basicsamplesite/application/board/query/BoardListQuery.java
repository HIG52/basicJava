package com.example.basicsamplesite.application.board.query;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 게시글 목록 조회 쿼리 - Presentation Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class BoardListQuery {
    private final int page;
    private final int size;
    private final String search;

    public BoardListQuery(int page, int size, String search) {
        this.page = page;
        this.size = size;
        this.search = search;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size); // 1-based to 0-based conversion
    }
}
