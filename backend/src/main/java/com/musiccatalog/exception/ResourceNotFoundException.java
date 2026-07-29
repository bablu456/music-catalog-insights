package com.musiccatalog.exception;

/**
 * Exception thrown when a requested resource (e.g., entity by ID) cannot be found.
 */
public class ResourceNotFoundException extends BusinessException {
    
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }
}
