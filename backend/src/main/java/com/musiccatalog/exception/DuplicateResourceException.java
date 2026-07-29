package com.musiccatalog.exception;

/**
 * Exception thrown when attempting to create or update a resource 
 * that would violate unique constraints (e.g., duplicate email or ISRC).
 */
public class DuplicateResourceException extends BusinessException {
    
    public DuplicateResourceException(String message) {
        super(message, "DUPLICATE_RESOURCE");
    }
}
