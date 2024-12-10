package com.example.securitytest.service;

import com.example.securitytest.entity.User;
import com.example.securitytest.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean registerUser(String username, String password, String role) {

        try{
            String encodedPassword = passwordEncoder.encode(password);
            User user = User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .role(role)
                    .build();
            userRepository.save(user);
            return true;
        }catch (DataIntegrityViolationException e){
            return false;
        }
    }
}
