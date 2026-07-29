package com.musiccatalog.validation.messages;

/**
 * Stores standardized validation error messages.
 * Using a dedicated class ensures consistency across all DTOs and simplifies future i18n migration.
 */
public final class ValidationMessages {

    private ValidationMessages() {
        // Prevent instantiation
    }

    public static final String NOT_BLANK_MSG = "Field cannot be blank";
    public static final String INVALID_PATTERN_MSG = "Format is invalid";
    public static final String POSITIVE_MSG = "Value must be positive";
    public static final String PASSWORD_REQUIREMENTS_MSG = "Password must be at least 8 characters, contain one uppercase, one lowercase, one number, and one special character";
}
