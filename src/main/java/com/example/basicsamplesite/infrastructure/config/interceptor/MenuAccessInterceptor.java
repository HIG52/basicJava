package com.example.basicsamplesite.infrastructure.config.interceptor;

import com.example.basicsamplesite.application.menu.service.RoleMenuPermissionService;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import com.example.basicsamplesite.infrastructure.config.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuAccessInterceptor implements HandlerInterceptor {
    
    private final RoleMenuPermissionService roleMenuPermissionService;
    
    // 권한 검사를 하지 않을 경로들
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/auth",
            "/role-menu-permissions/my-menus",
            "/role-menu-permissions/check-access",
            "/error",
            "/favicon.ico",
            "/admin/**"
    );
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler) throws Exception {
        
        String requestPath = request.getRequestURI();
        String method = request.getMethod();
        
        log.debug("MenuAccessInterceptor - Path: {}, Method: {}", requestPath, method);
        
        // 제외 경로 체크
        if (isExcludedPath(requestPath)) {
            log.debug("Excluded path, allowing access: {}", requestPath);
            return true;
        }
        
        // 정적 리소스나 OPTIONS 요청은 통과
        if (isStaticResource(requestPath) || "OPTIONS".equals(method)) {
            return true;
        }
        
        // 스프링 시큐리티에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 비로그인 유저는 등록된 메뉴에 접근 불가
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            // 경로를 메뉴 경로로 매핑
            String menuPath = mapToMenuPath(requestPath);

            // 해당 경로가 메뉴로 등록되어 있는지 확인
            boolean isRegisteredMenu = roleMenuPermissionService.isRegisteredMenu(menuPath);

            if (isRegisteredMenu) {
                log.warn("Anonymous user denied access to registered menu: {}", requestPath);
                sendUnauthorizedResponse(response, "로그인이 필요한 메뉴입니다.");
                return false;
            }

            log.debug("Anonymous user, allowing access to non-registered path: {}", requestPath);
            return true;
        }
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserRole userRole = userDetails.getUser().getRole();
        
        // 관리자는 admin으로 시작하는 경로만 무조건 접근 가능
        if (UserRole.ADMIN.equals(userRole) && requestPath.startsWith("/admin")) {
            log.debug("Admin access granted for admin path: {}", requestPath);
            return true;
        }
        
        // 모든 사용자(관리자 포함)는 일반 메뉴에 대해 역할 기반 권한 확인 (블랙리스트 방식)
        try {
            // 경로 매핑: 실제 컨트롤러 경로를 메뉴 경로로 변환
            String menuPath = mapToMenuPath(requestPath);
            
            // 블랙리스트 방식: 메뉴가 존재하지 않으면 접근 허용
            // 메뉴가 존재하고 해당 Role이 차단되어 있으면 접근 거부
            boolean isBlocked = roleMenuPermissionService.isMenuBlocked(userRole, menuPath);
            if (isBlocked) {
                log.warn("Access denied for userRole: {} to path: {} (menu: {})", userRole, requestPath, menuPath);
                sendForbiddenResponse(response, "해당 메뉴에 접근할 권한이 없습니다.");
                return false;
            }
            
            log.debug("Access granted for userRole: {} to path: {} (menu: {})", userRole, requestPath, menuPath);
            return true;
            
        } catch (Exception e) {
            log.error("Error checking menu access for userRole: {} to path: {}", userRole, requestPath, e);
            sendInternalServerErrorResponse(response, "권한 확인 중 오류가 발생했습니다.");
            return false;
        }
    }
    
    private boolean isExcludedPath(String requestPath) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(requestPath::startsWith);
    }
    
    /**
     * 실제 컨트롤러 경로를 메뉴 경로로 매핑
     */
    private String mapToMenuPath(String requestPath) {
        // /qnas/** -> /qnas 로 매핑 (실제 컨트롤러 경로 그대로)
        if (requestPath.startsWith("/qnas")) {
            return "/qnas";
        }
        
        // /boards/** -> /boards 로 매핑
        if (requestPath.startsWith("/boards")) {
            return "/boards";
        }
        
        // /notices/** -> /notices 로 매핑  
        if (requestPath.startsWith("/notices")) {
            return "/notices";
        }
        
        // 추후 다른 경로 매핑 필요시 여기에 추가
        
        return requestPath;
    }
    
    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith("/css/") ||
               requestPath.startsWith("/js/") ||
               requestPath.startsWith("/images/") ||
               requestPath.startsWith("/static/") ||
               requestPath.endsWith(".css") ||
               requestPath.endsWith(".js") ||
               requestPath.endsWith(".png") ||
               requestPath.endsWith(".jpg") ||
               requestPath.endsWith(".jpeg") ||
               requestPath.endsWith(".gif") ||
               requestPath.endsWith(".ico");
    }
    
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(
                "{\"success\": false, \"message\": \"%s\", \"errorCode\": \"UNAUTHORIZED\"}", 
                message
        ));
    }
    
    private void sendForbiddenResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(
                "{\"success\": false, \"message\": \"%s\", \"errorCode\": \"ACCESS_DENIED\"}", 
                message
        ));
    }
    
    private void sendInternalServerErrorResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(
                "{\"success\": false, \"message\": \"%s\", \"errorCode\": \"INTERNAL_SERVER_ERROR\"}", 
                message
        ));
    }
}
