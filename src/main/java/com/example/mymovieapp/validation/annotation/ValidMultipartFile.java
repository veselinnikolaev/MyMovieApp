package com.example.mymovieapp.validation.annotation;

import com.example.mymovieapp.validation.validator.ValidMultipartFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidMultipartFileValidator.class)
public @interface ValidMultipartFile {
    String message() default "Invalid file!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
