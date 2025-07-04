package com.example.basicsamplesite.infrastructure.config;

import com.example.basicsamplesite.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Spring Security UserDetails 구현체
 */
public class UserDetailsImpl implements UserDetails {
    
    private final User user;
    
    public UserDetailsImpl(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getUserId(); // 로그인 시 사용하는 userId를 username으로 사용
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (현재는 항상 true)
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (현재는 항상 true)
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보 만료 여부 (현재는 항상 true)
    }
    
    @Override
    public boolean isEnabled() {
        return user.getIsActive(); // 계정 활성화 여부
    }
}
