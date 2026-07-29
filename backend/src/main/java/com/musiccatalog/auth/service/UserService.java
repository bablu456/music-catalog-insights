package com.musiccatalog.auth.service;

import com.musiccatalog.auth.dto.AuthResponse;
import com.musiccatalog.auth.dto.LoginRequest;
import com.musiccatalog.auth.dto.RegisterRequest;

public interface UserService {
    AuthResponse registerUser(RegisterRequest registerRequest);
    AuthResponse loginUser(LoginRequest loginRequest);
}
