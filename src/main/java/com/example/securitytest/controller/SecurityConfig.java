package com.example.securitytest.controller;

import com.example.securitytest.entity.CustomUserDetails;
import com.example.securitytest.repo.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                //유저 정보 CustomUserDetails에 저장
                .map(user -> new CustomUserDetails(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll() //전부 접속 가능
                        .requestMatchers("/main").authenticated() //로그인해야 접속 가능
                        .requestMatchers("/admin").hasRole("ADMIN") //어드민만 가능
                        .requestMatchers("").hasAnyRole("ADMIN", "STORE")
                        // .requestMatchers("권한 설정할 주소").hasRole("허용할 권한")or.hasAnyRole(여러게 권한)
                        .anyRequest().authenticated()
                )

                //로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/main", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                //로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // 로그아웃 후 로그인 페이지로 리다이렉트
                        .logoutSuccessUrl("/login?logout=true") // 로그아웃 성공 메시지
                        .permitAll()
                );
        return http.build();
    }
}
