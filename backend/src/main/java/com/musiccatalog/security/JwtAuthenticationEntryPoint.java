package com.musiccatalog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musiccatalog.common.ApiError;
import com.musiccatalog.common.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
                         
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ApiError apiError = ApiError.builder()
                .code("UNAUTHORIZED")
                .message("Unauthorized: " + authException.getMessage())
                .build();

        ApiResponse<Object> apiResponse = ApiResponse.error(apiError, HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}
