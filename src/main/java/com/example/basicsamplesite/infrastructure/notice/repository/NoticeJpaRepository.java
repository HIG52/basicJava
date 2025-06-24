package com.example.basicsamplesite.infrastructure.notice.repository;

import com.example.basicsamplesite.domain.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Notice JPA Repository
 */
public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {
    
    /**
     * 삭제되지 않은 공지사항 목록 조회 (페이징, 최신순)
     */
    @Query(value = "SELECT * FROM notices WHERE IS_DELETED = 0 AND " +
            "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%') " +
            "ORDER BY CREATED_AT DESC",
            countQuery = "SELECT COUNT(*) FROM notices WHERE IS_DELETED = 0 AND " +
                    "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%')",
            nativeQuery = true)
    Page<Notice> findAllActiveOrderByCreatedAtDesc(Pageable pageable, @Param("search") String search);
    
    /**
     * 삭제되지 않은 공지사항 개수
     */
    @Query("SELECT COUNT(n) FROM Notice n WHERE n.isDeleted = false")
    long countActive();
    
    /**
     * 검색 조건에 해당하는 삭제되지 않은 공지사항 개수
     */
    @Query(value = "SELECT COUNT(*) FROM notices WHERE IS_DELETED = 0 AND " +
            "(:search IS NULL OR TITLE LIKE '%' || :search || '%' OR CONTENT LIKE '%' || :search || '%')",
            nativeQuery = true)
    long countActiveWithSearch(@Param("search") String search);
    
    /**
     * ID로 삭제되지 않은 공지사항 조회
     */
    @Query("SELECT n FROM Notice n WHERE n.id = :id AND n.isDeleted = false")
    java.util.Optional<Notice> findByIdAndNotDeleted(Long id);
}
