package com.patient.authservice.service;

import com.patient.authservice.dto.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {

    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    /**
     * Validates a JWT. Returns true if valid, false otherwise.
     */
    boolean validateToken(String token);
}
