package com.musiccatalog.exception;

import com.musiccatalog.common.ApiError;
import com.musiccatalog.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Centralized exception handler for the entire application.
 * Captures thrown exceptions and translates them into a standardized ApiResponse structure.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles 404 Not Found exceptions.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
            
        ApiError apiError = ApiError.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
                
        return buildResponse(apiError, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles 409 Conflict exceptions for duplicate records.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResourceException(
            DuplicateResourceException ex, HttpServletRequest request) {
            
        ApiError apiError = ApiError.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
                
        return buildResponse(apiError, HttpStatus.CONFLICT, request);
    }

    /**
     * Handles general Business exceptions (e.g. external API errors).
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {
            
        ApiError apiError = ApiError.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
                
        HttpStatus status = "EXTERNAL_API_ERROR".equals(ex.getErrorCode()) ? 
                HttpStatus.BAD_GATEWAY : HttpStatus.BAD_REQUEST;
                
        return buildResponse(apiError, status, request);
    }

    /**
     * Handles 400 Bad Request from invalid arguments.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {
            
        ApiError apiError = ApiError.builder()
                .code("BAD_REQUEST")
                .message(ex.getMessage())
                .build();
                
        return buildResponse(apiError, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles 400 Bad Request for Jakarta Validation (@Valid) failures.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
            
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiError apiError = ApiError.builder()
                .code("VALIDATION_FAILED")
                .message("Input validation failed")
                .details(details)
                .build();
                
        return buildResponse(apiError, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Fallback handler for all unexpected 500 Internal Server Errors.
     * Logs the full stack trace securely but hides it from the client API response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception ex, HttpServletRequest request) {
            
        // Securely log the exception internally
        log.error("Unexpected error occurred: ", ex);
        
        ApiError apiError = ApiError.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .build();
                
        return buildResponse(apiError, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Helper method to construct the final API envelope.
     */
    private ResponseEntity<ApiResponse<Void>> buildResponse(ApiError apiError, HttpStatus status, HttpServletRequest request) {
        ApiResponse<Void> response = ApiResponse.error(apiError, status.value(), request.getRequestURI());
        return new ResponseEntity<>(response, status);
    }
}
