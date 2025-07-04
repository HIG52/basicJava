package com.example.basicsamplesite.domain.user.repository;

import com.example.basicsamplesite.domain.user.dto.UserDetailResponse;
import com.example.basicsamplesite.domain.user.dto.UserSummaryResponse;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.infrastructure.common.dto.PageRequest;
import com.example.basicsamplesite.infrastructure.common.dto.PageResponse;

import java.util.Optional;

/**
 * User Repository 인터페이스
 */
public interface UserRepository {

    User save(User user);

    PageResponse<UserSummaryResponse> findAll(PageRequest pageRequest, String search);

    Optional<UserDetailResponse> findDetailById(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    boolean existsByUserId(String userId);

    boolean existsByUsername(String username);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    java.util.List<User> findAllWithPaging(int page, int size, String search);

    long countWithSearch(String search);
}
