package com.example.basicsamplesite.presentation.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 인증 관련 컨트롤러 (스프링 시큐리티 연계용)
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        
        if (logout != null) {
            model.addAttribute("message", "성공적으로 로그아웃되었습니다.");
        }
        
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
}
