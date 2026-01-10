package com.patient.authservice.service.impl;

import com.patient.authservice.dto.LoginRequestDTO;
import com.patient.authservice.service.AuthService;
import com.patient.authservice.service.UserService;
import com.patient.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        if (loginRequestDTO == null
                || loginRequestDTO.getEmail() == null
                || loginRequestDTO.getPassword() == null) {
            return Optional.empty();
        }

        return userService.findByEmail(loginRequestDTO.getEmail().trim())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
