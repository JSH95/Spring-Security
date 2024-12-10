package com.example.securitytest.controller;

import com.example.securitytest.entity.CustomUserDetails;
import com.example.securitytest.entity.User;
import com.example.securitytest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SecurityConfig securityConfig;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, //로그인 실패시 파라미터
                            @RequestParam(value = "logout", required = false) String logout, //로그아웃시 파라미터
                            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "회원정보가 없습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃 되었습니다.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(String username, String password, String role, Model model) {
        boolean result = userService.registerUser(username, password, role);
        if (!result) {
            model.addAttribute("A", "중복된 유저가 존재합니다.");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal CustomUserDetails user, Model model) {
//        저장된 로그인 정보 받아 보는 방법 @AuthenticationPrincipal CustomUserDetails user
//        String username = (user != null) ? user.getUsername() : "알 수 없음";
//        model.addAttribute("username", username);
        return "main";
    }
}
