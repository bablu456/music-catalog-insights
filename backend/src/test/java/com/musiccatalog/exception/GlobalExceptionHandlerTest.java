package com.musiccatalog.exception;

import com.musiccatalog.common.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleResourceNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not found", response.getBody().getError().getMessage());
    }

    @Test
    void handleDuplicateResourceException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        DuplicateResourceException ex = new DuplicateResourceException("Duplicate");
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleDuplicateResourceException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Duplicate", response.getBody().getError().getMessage());
    }

    @Test
    void handleBusinessException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        BusinessException ex = new BusinessException("Business error", "ERR_CODE");
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleBusinessException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Business error", response.getBody().getError().getMessage());
    }
}
