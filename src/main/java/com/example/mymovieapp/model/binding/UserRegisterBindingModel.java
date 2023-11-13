package com.example.mymovieapp.model.binding;

import com.example.mymovieapp.validation.annotation.ConfirmPassword;
import com.example.mymovieapp.validation.annotation.UniqueEmail;
import com.example.mymovieapp.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterBindingModel {
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    @UniqueUsername
    @NotBlank
    private String username;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    @NotBlank
    private String password;
    @ConfirmPassword
    @NotBlank
    private String confirmPassword;
    @Email(message = "Invalid email!")
    @UniqueEmail
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
