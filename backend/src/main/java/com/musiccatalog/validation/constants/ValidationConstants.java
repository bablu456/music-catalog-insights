package com.musiccatalog.validation.constants;

/**
 * Stores standard regex patterns and numerical constraints for validation.
 * Separating constants from messages allows for cleaner reuse.
 */
public final class ValidationConstants {

    private ValidationConstants() {
        // Prevent instantiation
    }

    // Standard ISRC (International Standard Recording Code) pattern
    public static final String ISRC_PATTERN = "^[A-Z]{2}-\\w{3}-\\d{2}-\\d{5}$";
    
    // Strong password pattern
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
}
