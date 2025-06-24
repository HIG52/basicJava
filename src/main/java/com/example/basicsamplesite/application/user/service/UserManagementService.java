package com.example.basicsamplesite.application.user.service;

import com.example.basicsamplesite.application.user.command.CreateUserCommand;
import com.example.basicsamplesite.application.user.command.UpdateUserCommand;
import com.example.basicsamplesite.application.user.dto.UserListManagementApplicationDto;
import com.example.basicsamplesite.application.user.dto.UserManagementApplicationDto;
import com.example.basicsamplesite.application.user.query.UserListQuery;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 관리 관련 Application Service
 */
@Service
@Transactional
public class UserManagementService {
    
    private final UserRepository userRepository;
    
    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserListManagementApplicationDto getUserList(UserListQuery query) {
        List<User> users = userRepository.findAllWithPaging(
                query.getPage(), 
                query.getSize(), 
                query.getSearch()
        );
        long totalItems = userRepository.countWithSearch(query.getSearch());
        
        List<UserManagementApplicationDto> userList = users.stream()
                .map(this::convertToUserManagementApplicationDto)
                .collect(Collectors.toList());
        
        int totalPages = (int) Math.ceil((double) totalItems / query.getSize());
        
        return new UserListManagementApplicationDto(
                userList,
                totalItems,
                query.getPage(),
                query.getSize(),
                totalPages
        );
    }

    @Transactional(readOnly = true)
    public UserManagementApplicationDto getUserDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        
        return convertToUserManagementApplicationDto(user);
    }

    public UserManagementApplicationDto createUser(CreateUserCommand command) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        User user = User.builder()
                .name(command.getName())
                .email(command.getEmail())
                .password("temp123!") // 임시 비밀번호
                .role(User.UserRole.valueOf(command.getRole().toUpperCase()))
                .isActive(true)
                .build();
        
        User savedUser = userRepository.save(user);
        return convertToUserManagementApplicationDto(savedUser);
    }

    public UserManagementApplicationDto updateUser(UpdateUserCommand command) {
        User user = userRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        
        // 이메일 중복 확인 (자신 제외)
        if (!user.getEmail().equals(command.getEmail()) && userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        user.updateUser(
                command.getName(),
                command.getEmail(),
                User.UserRole.valueOf(command.getRole().toUpperCase()),
                null, // phone은 관리 API에서 제외
                true // 활성 상태 유지
        );
        
        User savedUser = userRepository.save(user);
        return convertToUserManagementApplicationDto(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        }
        
        userRepository.deleteById(id);
    }

    private UserManagementApplicationDto convertToUserManagementApplicationDto(User user) {
        return new UserManagementApplicationDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name().toLowerCase(),
                user.getCreatedAt().toLocalDate()
        );
    }
}
