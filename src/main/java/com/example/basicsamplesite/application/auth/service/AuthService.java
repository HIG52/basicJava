package com.example.basicsamplesite.application.auth.service;

import com.example.basicsamplesite.application.auth.command.LoginCommand;
import com.example.basicsamplesite.application.auth.command.SignupCommand;
import com.example.basicsamplesite.application.auth.dto.AuthApplicationDto;
import com.example.basicsamplesite.application.auth.dto.UserApplicationDto;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public AuthApplicationDto login(LoginCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));
        
        if (!user.isActive()) {
            throw new IllegalArgumentException("비활성화된 계정입니다");
        }
        
        if (!user.checkPassword(command.getPassword())) {
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
        // 이메일 중복 확인
        if (userRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        // 사용자 생성
        User user = User.builder()
                .name(command.getName())
                .email(command.getEmail())
                .password(command.getPassword()) // 실제로는 암호화 필요
                .role(User.UserRole.USER)
                .isActive(true)
                .build();
        
        User savedUser = userRepository.save(user);
        
        return convertToUserApplicationDto(savedUser);
    }

    private String generateJwtToken(User user) {
        // 실제로는 JWT 라이브러리 사용
        return "jwt_token_" + user.getId() + "_" + System.currentTimeMillis();
    }

    private UserApplicationDto convertToUserApplicationDto(User user) {
        return new UserApplicationDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name().toLowerCase()
        );
    }
}
