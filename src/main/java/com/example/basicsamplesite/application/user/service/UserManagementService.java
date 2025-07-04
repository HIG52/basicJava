package com.example.basicsamplesite.application.user.service;

import com.example.basicsamplesite.application.user.command.CreateUserCommand;
import com.example.basicsamplesite.application.user.command.UpdateUserCommand;
import com.example.basicsamplesite.application.user.dto.UserListManagementApplicationDto;
import com.example.basicsamplesite.application.user.dto.UserManagementApplicationDto;
import com.example.basicsamplesite.application.user.query.UserListQuery;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    
    public UserManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        // 사용자 ID 중복 확인
        if (userRepository.existsByUserId(command.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다");
        }
        
        // 이메일 중복 확인
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        // 선택 필드들은 빈 값이면 null로 저장
        String phone = isBlankOrNull(command.getPhone()) ? null : command.getPhone();
        String address = isBlankOrNull(command.getAddress()) ? null : command.getAddress();
        String zipcode = isBlankOrNull(command.getZipcode()) ? null : command.getZipcode();
        String addressDetail = isBlankOrNull(command.getAddressDetail()) ? null : command.getAddressDetail();
        
        User user = User.builder()
                .userId(command.getUserId())
                .username(command.getUsername())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword())) // 비밀번호 해싱
                .role(User.UserRole.valueOf(command.getRole().toUpperCase()))
                .phone(phone)
                .address(address)
                .zipcode(zipcode)
                .addressDetail(addressDetail)
                .isActive(true)
                .build();
        
        User savedUser = userRepository.save(user);
        return convertToUserManagementApplicationDto(savedUser);
    }

    public UserManagementApplicationDto updateUser(UpdateUserCommand command) {
        User user = userRepository.findById(command.getDbId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        
        // 사용자 ID 중복 확인 (자신 제외)
        if (!user.getUserId().equals(command.getUserId()) && userRepository.existsByUserId(command.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다");
        }
        
        // 이메일 중복 확인 (자신 제외)
        if (!user.getEmail().equals(command.getEmail()) && userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        // 선택 필드들은 빈 값이면 null로 저장
        String phone = isBlankOrNull(command.getPhone()) ? null : command.getPhone();
        String address = isBlankOrNull(command.getAddress()) ? null : command.getAddress();
        String zipcode = isBlankOrNull(command.getZipcode()) ? null : command.getZipcode();
        String addressDetail = isBlankOrNull(command.getAddressDetail()) ? null : command.getAddressDetail();
        
        user.updateUserWithAddress(
                command.getUserId(),
                command.getUsername(),
                command.getEmail(),
                User.UserRole.valueOf(command.getRole().toUpperCase()),
                phone,
                address,
                zipcode,
                addressDetail,
                user.getBirthDate(), // 기존 생년월일 유지
                true // 활성 상태 유지
        );
        
        // 비밀번호가 제공되면 변경
        if (!isBlankOrNull(command.getPassword())) {
            user.changePassword(passwordEncoder.encode(command.getPassword()));
        }
        
        User savedUser = userRepository.save(user);
        return convertToUserManagementApplicationDto(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        }
        
        userRepository.deleteById(id);
    }
    
    private boolean isBlankOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    private UserManagementApplicationDto convertToUserManagementApplicationDto(User user) {
        return new UserManagementApplicationDto(
                user.getId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name().toLowerCase(),
                user.getPhone(),
                user.getAddress(),
                user.getZipcode(),
                user.getAddressDetail(),
                user.getCreatedAt().toLocalDate()
        );
    }
}
