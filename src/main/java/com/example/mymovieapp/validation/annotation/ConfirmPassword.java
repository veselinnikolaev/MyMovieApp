package com.example.mymovieapp.validation.annotation;

import com.example.mymovieapp.validation.validator.ConfirmPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPassword {

    String message() default "Confirm password does NOT match!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
