package com.example.basicsamplesite.presentation.user.controller;

import com.example.basicsamplesite.application.user.command.CreateUserCommand;
import com.example.basicsamplesite.application.user.command.UpdateUserCommand;
import com.example.basicsamplesite.application.user.dto.UserListManagementApplicationDto;
import com.example.basicsamplesite.application.user.dto.UserManagementApplicationDto;
import com.example.basicsamplesite.application.user.query.UserListQuery;
import com.example.basicsamplesite.application.user.service.UserManagementService;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.user.dto.UserManagementListResponse;
import com.example.basicsamplesite.presentation.user.dto.UserManagementRequest;
import com.example.basicsamplesite.presentation.user.dto.UserManagementResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 관리 관련 Controller
 */
@RestController
@RequestMapping("/users")
public class UserManagementController {
    
    private final UserManagementService userManagementService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }
    
    /**
     * 회원 목록 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<UserManagementListResponse>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        try {
            // Presentation → Application 변환
            UserListQuery query = new UserListQuery(page, size, search);
            
            // Application Service 호출
            UserListManagementApplicationDto listDto = userManagementService.getUserList(query);
            
            // Application DTO → Presentation Response 변환
            List<UserManagementResponse> users = listDto.getList().stream()
                    .map(this::convertToUserManagementResponse)
                    .collect(Collectors.toList());
            
            UserManagementListResponse response = new UserManagementListResponse(
                    users,
                    listDto.getTotalItems(),
                    listDto.getCurrentPage(),
                    listDto.getItemsPerPage(),
                    listDto.getTotalPages()
            );
            
            return ResponseEntity.ok(ApiResponse.success("회원 목록 조회 성공", response));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("회원 목록 조회 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 회원 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserManagementResponse>> getUserDetail(@PathVariable Long id) {
        try {
            // Application Service 호출
            UserManagementApplicationDto userDto = userManagementService.getUserDetail(id);
            
            // Application DTO → Presentation Response 변환
            UserManagementResponse response = convertToUserManagementResponse(userDto);
            
            return ResponseEntity.ok(ApiResponse.success("회원 상세 조회 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("회원 상세 조회 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 회원 추가
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserManagementResponse>> createUser(@Valid @RequestBody UserManagementRequest request) {
        try {
            // Presentation DTO → Application Command 변환
            CreateUserCommand command = new CreateUserCommand(
                    request.getName(), 
                    request.getEmail(), 
                    request.getRole()
            );
            
            // Application Service 호출
            UserManagementApplicationDto userDto = userManagementService.createUser(command);
            
            // Application DTO → Presentation Response 변환
            UserManagementResponse response = convertToUserManagementResponse(userDto);
            
            return ResponseEntity.ok(ApiResponse.success("회원 추가 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("회원 추가 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 회원 정보 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserManagementResponse>> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UserManagementRequest request) {
        try {
            // Presentation DTO → Application Command 변환
            UpdateUserCommand command = new UpdateUserCommand(
                    id, 
                    request.getName(), 
                    request.getEmail(), 
                    request.getRole()
            );
            
            // Application Service 호출
            UserManagementApplicationDto userDto = userManagementService.updateUser(command);
            
            // Application DTO → Presentation Response 변환
            UserManagementResponse response = convertToUserManagementResponse(userDto);
            
            return ResponseEntity.ok(ApiResponse.success("회원 정보 수정 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("회원 정보 수정 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 회원 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Long id) {
        try {
            // Application Service 호출
            userManagementService.deleteUser(id);
            
            return ResponseEntity.ok(ApiResponse.success("회원 삭제 성공"));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("회원 삭제 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * UserManagementApplicationDto를 UserManagementResponse로 변환
     */
    private UserManagementResponse convertToUserManagementResponse(UserManagementApplicationDto dto) {
        return new UserManagementResponse(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getRole(),
                dto.getJoinDate().format(dateFormatter)
        );
    }
}
