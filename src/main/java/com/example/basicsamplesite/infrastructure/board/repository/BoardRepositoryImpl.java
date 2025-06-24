package com.example.basicsamplesite.infrastructure.board.repository;

import com.example.basicsamplesite.domain.board.entity.Board;
import com.example.basicsamplesite.domain.board.repository.BoardRepository;
import com.example.basicsamplesite.infrastructure.board.jpa.BoardJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Board Repository 구현체
 */
@Repository
public class BoardRepositoryImpl implements BoardRepository {
    
    private final BoardJpaRepository boardJpaRepository;
    
    public BoardRepositoryImpl(BoardJpaRepository boardJpaRepository) {
        this.boardJpaRepository = boardJpaRepository;
    }
    
    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(board);
    }
    
    @Override
    public Page<Board> findAllWithPaging(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size); // page는 1부터 시작
        
        Page<Board> boardPage;
        if (search != null && !search.trim().isEmpty()) {
            boardPage = boardJpaRepository.findAllWithSearch(search.trim(), pageable);
        } else {
            boardPage = boardJpaRepository.findByIsDeletedFalseOrderByCreatedAtDesc(pageable);
        }
        
        return boardPage;
    }
    
    @Override
    public long count(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return boardJpaRepository.countActiveWithSearch(search.trim());
        } else {
            return boardJpaRepository.countActive();
        }
    }
    
    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findByIdAndNotDeleted(id);
    }
    
    @Override
    public void deleteById(Long id) {
        boardJpaRepository.findByIdAndNotDeleted(id)
                .ifPresent(board -> {
                    board.delete(); // 소프트 삭제
                    boardJpaRepository.save(board);
                });
    }
    
    @Override
    public boolean existsById(Long id) {
        return boardJpaRepository.findByIdAndNotDeleted(id).isPresent();
    }
}
