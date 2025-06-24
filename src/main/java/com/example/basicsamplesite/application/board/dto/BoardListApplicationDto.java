package com.example.basicsamplesite.application.board.dto;

import com.example.basicsamplesite.domain.board.entity.Board;
import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 목록 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class BoardListApplicationDto {
    private final List<BoardApplicationDto> list;
    private final long totalItems;
    private final int currentPage;
    private final int itemsPerPage;
    private final int totalPages;

    public BoardListApplicationDto(List<BoardApplicationDto> list, long totalItems, 
                                  int currentPage, int itemsPerPage, int totalPages) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

    public static BoardListApplicationDto from(Page<Board> boardPage) {
        List<BoardApplicationDto> list = boardPage.getContent().stream()
            .map(BoardApplicationDto::from)
            .collect(Collectors.toList());
            
        return new BoardListApplicationDto(
            list,
            boardPage.getTotalElements(),
            boardPage.getNumber() + 1, // 0-based to 1-based conversion
            boardPage.getSize(),
            boardPage.getTotalPages()
        );
    }

}
