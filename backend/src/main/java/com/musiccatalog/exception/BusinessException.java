package com.musiccatalog.exception;

import lombok.Getter;

/**
 * Base class for all business logic exceptions in the application.
 * Allows passing an error code directly to the ApiError format.
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
