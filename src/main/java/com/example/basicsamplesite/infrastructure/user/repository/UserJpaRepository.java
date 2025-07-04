package com.example.basicsamplesite.infrastructure.user.repository;

import com.example.basicsamplesite.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * User JPA Repository
 */
public interface UserJpaRepository extends JpaRepository<User, Long> {
    
    /**
     * 사용자 ID, 사용자명 또는 이메일로 검색
     */
    @Query("SELECT u FROM User u WHERE " +
           "(:search IS NULL OR u.userId LIKE %:search% OR u.username LIKE %:search% OR u.email LIKE %:search%) " +
           "ORDER BY u.createdAt DESC")
    Page<User> findAllWithSearch(@Param("search") String search, Pageable pageable);
    
    /**
     * 전체 회원 조회
     */
    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC")
    Page<User> findAllOrdered(Pageable pageable);
    
    /**
     * 이메일로 회원 조회
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 사용자 ID로 회원 조회
     */
    Optional<User> findByUserId(String userId);
    
    /**
     * 사용자명으로 회원 조회
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 이메일 존재 여부 확인 (커스텀 쿼리)
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    
    /**
     * 사용자 ID 존재 여부 확인
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userId = :userId")
    boolean existsByUserId(@Param("userId") String userId);
    
    /**
     * 사용자명 존재 여부 확인
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);
    
    /**
     * 검색 조건에 따른 개수 조회
     */
    @Query("SELECT COUNT(u) FROM User u WHERE " +
           "(:search IS NULL OR u.userId LIKE %:search% OR u.username LIKE %:search% OR u.email LIKE %:search%)")
    long countWithSearch(@Param("search") String search);
    
    /**
     * 전체 회원 개수 조회
     */
    @Query("SELECT COUNT(u) FROM User u")
    long countAll();
}
