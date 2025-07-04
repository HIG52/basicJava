package com.example.basicsamplesite.presentation.auth.controller;

import com.example.basicsamplesite.application.auth.command.SignupCommand;
import com.example.basicsamplesite.application.auth.dto.UserApplicationDto;
import com.example.basicsamplesite.application.auth.service.AuthService;
import com.example.basicsamplesite.infrastructure.config.UserDetailsImpl;
import com.example.basicsamplesite.presentation.auth.dto.*;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 인증 관련 컨트롤러 (리액트 API용)
 */
@RestController
public class AuthController {
    
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 리액트용 로그인 API
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            // 스프링 시큐리티를 통한 인증 (userId를 username으로 사용)
            UsernamePasswordAuthenticationToken authRequest = 
                new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword());
            
            Authentication authResult = authenticationManager.authenticate(authRequest);
            
            // 인증 성공 시 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authResult);
            
            // UserDetailsImpl에서 사용자 정보 추출
            UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
            
            LoginResponse response = new LoginResponse(
                userDetails.getUsername(),
                userDetails.getUser().getRole().name()
            );
            
            return ResponseEntity.ok(ApiResponse.success("로그인이 성공했습니다", response));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("아이디 또는 비밀번호가 잘못되었습니다"));
        }
    }
    
    /**
     * 현재 로그인 사용자 정보 조회 API
     */
    @GetMapping("/api/auth/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("인증되지 않은 사용자입니다"));
        }
        
        UserInfoResponse response = new UserInfoResponse(
            userDetails.getUsername(),
            userDetails.getUser().getRole().name()
        );
        
        return ResponseEntity.ok(ApiResponse.success("사용자 정보 조회 성공", response));
    }
    
    /**
     * 리액트용 로그아웃 API
     */
    @PostMapping("/api/auth/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        try {
            // 현재 인증 정보 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.isAuthenticated() && 
                !authentication.getPrincipal().equals("anonymousUser")) {
                
                // 보안 컨텍스트 클리어
                SecurityContextHolder.clearContext();
                
                // 세션 무효화
                if (request.getSession(false) != null) {
                    request.getSession().invalidate();
                }
                
                return ResponseEntity.ok(ApiResponse.success("로그아웃이 완료되었습니다", null));
            } else {
                return ResponseEntity.status(401).body(ApiResponse.error("로그인되지 않은 사용자입니다"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("로그아웃 처리 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 사용자 페이지 회원가입 API (role: user 고정)
     */
    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiResponse.error(createErrorMessage("입력값 검증에 실패했습니다", bindingResult)));
        }
        
        try {
            SignupCommand command = new SignupCommand(
                    request.getUserId(),
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getPhone(),
                    request.getZipcode(),
                    request.getAddress(),
                    request.getAddressDetail()
            );
            
            UserApplicationDto userDto = authService.signup(command);
            
            SignupResponse response = new SignupResponse(
                    userDto.getId(),
                    userDto.getUserId(),
                    userDto.getUsername(),
                    userDto.getEmail(),
                    userDto.getRole(),
                    request.getPhone(),
                    request.getZipcode(),
                    request.getAddress(),
                    request.getAddressDetail()
            );
            
            return ResponseEntity.ok(ApiResponse.success("회원가입이 성공했습니다", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("회원가입 처리 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 관리자 페이지 회원가입 API (role 선택 가능)
     */
    @PostMapping("/api/admin/auth/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> adminSignup(@Valid @RequestBody AdminSignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiResponse.error(createErrorMessage("입력값 검증에 실패했습니다", bindingResult)));
        }
        
        try {
            SignupCommand command = new SignupCommand(
                    request.getUserId(),
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getRole(),
                    request.getPhone(),
                    request.getZipcode(),
                    request.getAddress(),
                    request.getAddressDetail()
            );
            
            UserApplicationDto userDto = authService.signup(command);
            
            SignupResponse response = new SignupResponse(
                    userDto.getId(),
                    userDto.getUserId(),
                    userDto.getUsername(),
                    userDto.getEmail(),
                    userDto.getRole(),
                    request.getPhone(),
                    request.getZipcode(),
                    request.getAddress(),
                    request.getAddressDetail()
            );
            
            return ResponseEntity.ok(ApiResponse.success("회원가입이 성공했습니다", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("회원가입 처리 중 오류가 발생했습니다"));
        }
    }
    
    private String createErrorMessage(String message, BindingResult bindingResult) {
        StringBuilder errorMessages = new StringBuilder(message);
        bindingResult.getFieldErrors().forEach(error -> 
            errorMessages.append(" - ").append(error.getDefaultMessage())
        );
        return errorMessages.toString();
    }
}
