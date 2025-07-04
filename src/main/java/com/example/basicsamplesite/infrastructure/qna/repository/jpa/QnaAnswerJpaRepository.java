package com.example.basicsamplesite.infrastructure.qna.repository.jpa;

import com.example.basicsamplesite.domain.qna.entity.QnaAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * QnaAnswer JPA Repository
 */
public interface QnaAnswerJpaRepository extends JpaRepository<QnaAnswer, Long> {
    
    /**
     * Q&A ID로 답변 목록 조회
     */
    List<QnaAnswer> findByQnaIdOrderByCreatedAtAsc(Long qnaId);
}
