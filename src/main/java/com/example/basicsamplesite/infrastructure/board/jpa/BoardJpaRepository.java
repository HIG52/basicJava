package com.example.basicsamplesite.infrastructure.board.jpa;

import com.example.basicsamplesite.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT * FROM board WHERE IS_DELETED = 0 AND " +
           "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%') " +
           "ORDER BY CREATED_AT DESC", 
           countQuery = "SELECT COUNT(*) FROM board WHERE IS_DELETED = 0 AND " +
           "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%')",
           nativeQuery = true)
    Page<Board> findAllWithSearch(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT * FROM board WHERE IS_DELETED = 0 ORDER BY CREATED_AT DESC", 
           countQuery = "SELECT COUNT(*) FROM board WHERE IS_DELETED = 0",
           nativeQuery = true)
    Page<Board> findByIsDeletedFalseOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT COUNT(b) FROM Board b WHERE b.authorId = :authorId AND b.isDeleted = false")
    Long countByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT COALESCE(SUM(b.views), 0) FROM Board b WHERE b.authorId = :authorId AND b.isDeleted = false")
    Long sumViewsByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT b FROM Board b WHERE b.id = :id AND b.isDeleted = false")
    java.util.Optional<Board> findByIdAndNotDeleted(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM board WHERE IS_DELETED = 0 AND " +
           "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%')",
           nativeQuery = true)
    long countActiveWithSearch(@Param("search") String search);

    @Query("SELECT COUNT(b) FROM Board b WHERE b.isDeleted = false")
    long countActive();
}
