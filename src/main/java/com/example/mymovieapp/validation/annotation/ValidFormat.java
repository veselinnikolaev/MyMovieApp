package com.example.mymovieapp.validation.annotation;

import com.example.mymovieapp.validation.validator.ValidFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidFormatValidator.class)
public @interface ValidFormat {
    String message() default "Invalid date format! Example: 2018-05-30";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
