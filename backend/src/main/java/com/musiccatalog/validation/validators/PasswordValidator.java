package com.musiccatalog.validation.validators;

import com.musiccatalog.validation.annotations.ValidPassword;
import com.musiccatalog.validation.constants.ValidationConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Implementation of the constraint validation for @ValidPassword.
 * Extends Jakarta's ConstraintValidator interface.
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PATTERN = Pattern.compile(ValidationConstants.PASSWORD_PATTERN);

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Initialization if needed before validation
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return PATTERN.matcher(password).matches();
    }
}
