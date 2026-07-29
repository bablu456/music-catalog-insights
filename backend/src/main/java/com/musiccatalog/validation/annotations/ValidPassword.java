package com.musiccatalog.validation.annotations;

import com.musiccatalog.validation.messages.ValidationMessages;
import com.musiccatalog.validation.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom annotation to enforce strong password constraints on DTO fields.
 * Validated by {@link PasswordValidator}.
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {

    String message() default ValidationMessages.PASSWORD_REQUIREMENTS_MSG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
