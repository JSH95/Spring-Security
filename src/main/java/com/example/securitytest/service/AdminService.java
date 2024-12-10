package com.example.securitytest.service;

import com.example.securitytest.entity.User;
import com.example.securitytest.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    final private UserRepository userRepository;
    public List<User> findUsers() {

            List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new RuntimeException("사용자가 없습니다.");
        }
            return userList;

    }
}
