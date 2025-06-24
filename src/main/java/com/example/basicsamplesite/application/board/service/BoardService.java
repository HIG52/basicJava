package com.example.basicsamplesite.application.board.service;

import com.example.basicsamplesite.application.board.command.CreateBoardCommand;
import com.example.basicsamplesite.application.board.command.UpdateBoardCommand;
import com.example.basicsamplesite.application.board.dto.BoardApplicationDto;
import com.example.basicsamplesite.application.board.dto.BoardListApplicationDto;
import com.example.basicsamplesite.application.board.query.BoardListQuery;
import com.example.basicsamplesite.domain.board.entity.Board;
import com.example.basicsamplesite.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardListApplicationDto getBoardList(BoardListQuery query) {
        Page<Board> boardPage = boardRepository.findAllWithPaging(
            query.getPage(), query.getSize(), query.getSearch()
        );
        
        return BoardListApplicationDto.from(boardPage);
    }

    public BoardApplicationDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
        // 조회수 증가
        board.incrementViews();
        boardRepository.save(board);
        
        return BoardApplicationDto.from(board);
    }

    @Transactional
    public BoardApplicationDto createBoard(CreateBoardCommand command) {
        String author = "익명"; // 임시로 설정, 추후 스프링시큐리티에서 사용자 정보 추출
        Long authorId = 1L; // 임시 사용자 ID, 추후 스프링시큐리티에서 사용자 ID 추출
        
        Board board = Board.createBoard(
            command.getTitle(),
            command.getContent(),
            author,
            authorId
        );
            
        Board savedBoard = boardRepository.save(board);
        return BoardApplicationDto.from(savedBoard);
    }

    @Transactional
    public BoardApplicationDto updateBoard(UpdateBoardCommand command) {
        Board board = boardRepository.findById(command.getId())
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + command.getId()));
        
        board.updateBoard(command.getTitle(), command.getContent());
        
        Board updatedBoard = boardRepository.save(board);
        return BoardApplicationDto.from(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
        boardRepository.deleteById(id);
    }
}
