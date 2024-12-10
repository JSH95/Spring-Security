package com.example.securitytest.controller;

import com.example.securitytest.entity.User;
import com.example.securitytest.service.AdminService;
import com.example.securitytest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/admin")
    public String admin(Model model){
        List<User> userList = adminService.findUsers();
        model.addAttribute("userList", userList);
        return "admin";
    }
}

