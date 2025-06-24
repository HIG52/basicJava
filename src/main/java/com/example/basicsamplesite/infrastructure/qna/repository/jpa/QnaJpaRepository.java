package com.example.basicsamplesite.infrastructure.qna.repository.jpa;

import com.example.basicsamplesite.domain.qna.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Qna JPA Repository
 */
public interface QnaJpaRepository extends JpaRepository<Qna, Long> {
    
    /**
     * 제목 또는 내용으로 검색 (Oracle 호환)
     */
    @Query(value = "SELECT * FROM qna WHERE " +
           "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%') " +
           "ORDER BY CREATED_AT DESC", 
           countQuery = "SELECT COUNT(*) FROM qna WHERE " +
           "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%')",
           nativeQuery = true)
    Page<Qna> findAllWithSearch(@Param("search") String search, Pageable pageable);
    
    /**
     * 전체 Q&A 조회 (Oracle 호환)
     */
    @Query(value = "SELECT * FROM qna ORDER BY CREATED_AT DESC", 
           countQuery = "SELECT COUNT(*) FROM qna",
           nativeQuery = true)
    Page<Qna> findAllOrdered(Pageable pageable);
    
    /**
     * 작성자별 Q&A 수 조회
     */
    @Query("SELECT COUNT(q) FROM Qna q WHERE q.authorId = :authorId")
    Long countByAuthorId(@Param("authorId") Long authorId);
    
    /**
     * 검색 조건에 따른 개수 조회 (Oracle 호환)
     */
    @Query(value = "SELECT COUNT(*) FROM qna WHERE " +
           "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%')",
           nativeQuery = true)
    long countWithSearch(@Param("search") String search);
    
    /**
     * 전체 QnA 개수 조회
     */
    @Query("SELECT COUNT(q) FROM Qna q")
    long countAll();
}
