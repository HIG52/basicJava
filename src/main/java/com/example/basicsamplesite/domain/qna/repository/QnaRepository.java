package com.example.basicsamplesite.domain.qna.repository;

import com.example.basicsamplesite.domain.qna.entity.Qna;

import java.util.List;
import java.util.Optional;

/**
 * QnA Repository 인터페이스
 */
public interface QnaRepository {

    Qna save(Qna qna);

    List<Qna> findAllWithPaging(int page, int size, String search);

    long count(String search);

    Optional<Qna> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
