package com.example.basicsamplesite.presentation.board.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class BoardListResponse {
    private final List<BoardResponse> list;
    private final long totalItems;
    private final int currentPage;
    private final int itemsPerPage;
    private final int totalPages;

    public BoardListResponse(List<BoardResponse> list, long totalItems,
                            int currentPage, int itemsPerPage, int totalPages) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

}
