package com.musiccatalog.auth.controller;

import com.musiccatalog.auth.dto.AuthResponse;
import com.musiccatalog.auth.dto.LoginRequest;
import com.musiccatalog.auth.dto.RegisterRequest;
import com.musiccatalog.auth.service.UserService;
import com.musiccatalog.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest requestBody, jakarta.servlet.http.HttpServletRequest request) {
        AuthResponse response = userService.registerUser(requestBody);
        return ApiResponse.success(response, HttpStatus.CREATED.value(), request.getRequestURI());
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and get token")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest requestBody, jakarta.servlet.http.HttpServletRequest request) {
        AuthResponse response = userService.loginUser(requestBody);
        return ApiResponse.ok(response, request.getRequestURI());
    }
}
