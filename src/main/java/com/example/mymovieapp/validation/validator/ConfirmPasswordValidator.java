package com.example.mymovieapp.validation.validator;

import com.example.mymovieapp.model.binding.UserRegisterBindingModel;
import com.example.mymovieapp.validation.annotation.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, String> {
    private static String passwordToConfirm;
    private static String confirmPassword;
    public static void getObject(UserRegisterBindingModel userRegisterBindingModel){
        passwordToConfirm = userRegisterBindingModel.getPassword();
        confirmPassword = userRegisterBindingModel.getConfirmPassword();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        confirmPassword = confirmPassword != null ? confirmPassword : "";
        return confirmPassword.equals(passwordToConfirm != null ? passwordToConfirm : "");
    }
}
