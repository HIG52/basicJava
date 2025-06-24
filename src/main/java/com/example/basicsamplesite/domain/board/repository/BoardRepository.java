package com.example.basicsamplesite.domain.board.repository;

import com.example.basicsamplesite.domain.board.entity.Board;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);

    Page<Board> findAllWithPaging(int page, int size, String search);

    long count(String search);

    Optional<Board> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
