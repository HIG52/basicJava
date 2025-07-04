package com.example.basicsamplesite.infrastructure.exception;

import com.example.basicsamplesite.infrastructure.exception.menu.DuplicateMenuPathException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuAccessDeniedException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuNotFoundException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuPermissionAlreadyExistsException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuPermissionNotFoundException;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 전역 예외 처리를 위한 핸들러
 * 모든 API의 일관된 에러 응답을 위해 사용
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 메뉴 관련 예외 처리
     */
    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleMenuNotFoundException(MenuNotFoundException e) {
        log.warn("Menu not found: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(MenuAccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMenuAccessDeniedException(MenuAccessDeniedException e) {
        log.warn("Menu access denied: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(DuplicateMenuPathException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateMenuPathException(DuplicateMenuPathException e) {
        log.warn("Duplicate menu path: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(MenuPermissionAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleMenuPermissionAlreadyExistsException(MenuPermissionAlreadyExistsException e) {
        log.warn("Menu permission already exists: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(MenuPermissionNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleMenuPermissionNotFoundException(MenuPermissionNotFoundException e) {
        log.warn("Menu permission not found: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
    }

    /**
     * 인증 실패 처리 (401) - 커스텀 예외 사용
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("인증에 실패했습니다. 로그인 정보를 확인해주세요."));
    }

    /**
     * 권한 없음 처리 (403) - 커스텀 예외 사용
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthorizationException(AuthorizationException e) {
        log.warn("Access denied: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("접근 권한이 없습니다"));
    }

    /**
     * 비즈니스 로직 예외 처리 (IllegalArgumentException)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgumentException occurred: {}", e.getMessage());
        
        // 404 에러로 처리할 메시지들
        if (e.getMessage().contains("존재하지 않는") || e.getMessage().contains("찾을 수 없습니다")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("요청한 리소스를 찾을 수 없습니다"));
        }
        
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    /**
     * 요청 데이터 검증 실패 처리 (@Valid 어노테이션)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("Validation failed: {}", e.getMessage());
        
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("잘못된 요청입니다: " + errorMessage));
    }

    /**
     * 제약 조건 위반 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("Constraint violation: {}", e.getMessage());
        
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("잘못된 요청입니다: " + errorMessage));
    }

    /**
     * 기타 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception e) {
        log.error("Unexpected error occurred", e);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("서버 내부 오류가 발생했습니다"));
    }

    /**
     * 커스텀 인증 예외
     */
    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }

    /**
     * 커스텀 권한 예외
     */
    public static class AuthorizationException extends RuntimeException {
        public AuthorizationException(String message) {
            super(message);
        }
    }
}
