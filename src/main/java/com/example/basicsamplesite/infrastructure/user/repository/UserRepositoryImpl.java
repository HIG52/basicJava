package com.example.basicsamplesite.infrastructure.user.repository;

import com.example.basicsamplesite.domain.user.dto.UserDetailResponse;
import com.example.basicsamplesite.domain.user.dto.UserSummaryResponse;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.domain.user.repository.UserRepository;
import com.example.basicsamplesite.infrastructure.common.dto.PageRequest;
import com.example.basicsamplesite.infrastructure.common.dto.PageResponse;
import com.example.basicsamplesite.infrastructure.common.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
    
    @Override
    public PageResponse<UserSummaryResponse> findAll(PageRequest pageRequest, String search) {
        Pageable pageable = PageUtils.toPageable(pageRequest);
        
        Page<User> userPage;
        if (search != null && !search.trim().isEmpty()) {
            userPage = userJpaRepository.findAllWithSearch(search.trim(), pageable);
        } else {
            userPage = userJpaRepository.findAllOrdered(pageable);
        }
        
        return PageUtils.toPageResponse(userPage, this::toUserSummaryResponse);
    }
    
    @Override
    public Optional<UserDetailResponse> findDetailById(Long id) {
        return userJpaRepository.findById(id)
                .map(this::toUserDetailResponse);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
    
    @Override
    public Optional<User> findByUserId(String userId) {
        return userJpaRepository.findByUserId(userId);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }
    
    @Override
    public boolean existsByUserId(String userId) {
        return userJpaRepository.existsByUserId(userId);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
    
    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
    
    @Override
    public List<User> findAllWithPaging(int page, int size, String search) {
        Pageable pageable = PageUtils.toPageable(new PageRequest(page, size));
        
        Page<User> userPage;
        if (search != null && !search.trim().isEmpty()) {
            userPage = userJpaRepository.findAllWithSearch(search.trim(), pageable);
        } else {
            userPage = userJpaRepository.findAllOrdered(pageable);
        }
        
        return userPage.getContent();
    }
    
    @Override
    public long countWithSearch(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return userJpaRepository.countWithSearch(search.trim());
        } else {
            return userJpaRepository.countAll();
        }
    }
    
    private UserSummaryResponse toUserSummaryResponse(User user) {
        return new UserSummaryResponse(
                user.getId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt().toLocalDate(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getIsActive(),
                user.getLastLoginAt()
        );
    }
    
    private UserDetailResponse toUserDetailResponse(User user) {
        return new UserDetailResponse(
                user.getId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt().toLocalDate(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getLastLoginAt(),
                user.getIsActive(),
                user.getPhone(),
                user.getAddress(),
                user.getZipcode(),
                user.getAddressDetail(),
                user.getBirthDate(),
                null // Service에서 설정
        );
    }
}
