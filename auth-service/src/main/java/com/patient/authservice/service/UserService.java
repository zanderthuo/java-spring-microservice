package com.patient.authservice.service;

import com.patient.authservice.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
