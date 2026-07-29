package com.musiccatalog.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Represents a structured API error returned to the client.
 * Contains the error code, message, and optional validation details.
 * Instances of this class are immutable.
 */
@Getter
@Builder
public class ApiError {
    
    /**
     * High-level error message meant for the client or user.
     */
    private final String message;

    /**
     * Internal or specific error code (e.g., "VALIDATION_FAILED", "RESOURCE_NOT_FOUND").
     */
    private final String code;

    /**
     * Optional list of specific field errors or details.
     */
    private final List<String> details;
}
