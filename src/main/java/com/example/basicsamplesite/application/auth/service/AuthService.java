package com.example.basicsamplesite.application.auth.service;

import com.example.basicsamplesite.application.auth.command.LoginCommand;
import com.example.basicsamplesite.application.auth.command.SignupCommand;
import com.example.basicsamplesite.application.auth.dto.AuthApplicationDto;
import com.example.basicsamplesite.application.auth.dto.UserApplicationDto;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public AuthApplicationDto login(LoginCommand command) {
        // command.getUsername()은 실제로는 userId임 (스프링 시큐리티 호환성을 위해 username 필드명 유지)
        User user = userRepository.findByUserId(command.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다"));
        
        if (!user.isActive()) {
            throw new IllegalArgumentException("비활성화된 계정입니다");
        }
        
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        
        // 마지막 로그인 시간 업데이트
        user.updateLastLoginAt();
        
        // JWT 토큰 생성 (간단히 구현)
        String token = generateJwtToken(user);
        
        UserApplicationDto userDto = convertToUserApplicationDto(user);
        
        return new AuthApplicationDto(token, userDto);
    }

    public UserApplicationDto signup(SignupCommand command) {
        // 사용자 ID 중복 확인
        if (userRepository.existsByUserId(command.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다");
        }
        
        // 이메일 중복 확인
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        // 역할 검증
        User.UserRole userRole;
        try {
            userRole = User.UserRole.valueOf(command.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 역할입니다");
        }
        
        // 선택 필드들은 빈 값이면 null로 저장
        String phone = isBlankOrNull(command.getPhone()) ? null : command.getPhone();
        String zipcode = isBlankOrNull(command.getZipcode()) ? null : command.getZipcode();
        String address = isBlankOrNull(command.getAddress()) ? null : command.getAddress();
        String addressDetail = isBlankOrNull(command.getAddressDetail()) ? null : command.getAddressDetail();
        
        // 사용자 생성
        User user = User.builder()
                .userId(command.getUserId())
                .username(command.getUsername())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword())) // 비밀번호 해싱
                .role(userRole)
                .phone(phone)
                .zipcode(zipcode)
                .address(address)
                .addressDetail(addressDetail)
                .isActive(true)
                .build();
        
        User savedUser = userRepository.save(user);
        
        return convertToUserApplicationDto(savedUser);
    }
    
    private boolean isBlankOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String generateJwtToken(User user) {
        // 실제로는 JWT 라이브러리 사용
        return "jwt_token_" + user.getId() + "_" + System.currentTimeMillis();
    }

    private UserApplicationDto convertToUserApplicationDto(User user) {
        return new UserApplicationDto(
                user.getId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name().toLowerCase()
        );
    }
}
