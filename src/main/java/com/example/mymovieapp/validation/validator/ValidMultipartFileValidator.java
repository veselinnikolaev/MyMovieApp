package com.example.mymovieapp.validation.validator;

import com.example.mymovieapp.validation.annotation.ValidMultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidMultipartFileValidator implements ConstraintValidator<ValidMultipartFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile.isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Insert photo!")
                    .addConstraintViolation();
            return false;
        }
        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Only JPG and PNG images are allowed.")
                    .addConstraintViolation();
            return false;
        }
        if(multipartFile.getSize() > 5242880){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("File too big, max size = 5 MB.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

        private boolean isSupportedContentType(String contentType){
            var supportedContents = List.of("image/jpg", "image/jpeg", "image/png");
            return supportedContents.contains(contentType);
        }
    }
