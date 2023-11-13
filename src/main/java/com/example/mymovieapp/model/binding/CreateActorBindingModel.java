package com.example.mymovieapp.model.binding;

import com.example.mymovieapp.validation.annotation.ValidMultipartFile;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CreateActorBindingModel {
    @NotBlank(message = "Name cannot be empty!")
    private String name;
    @NotBlank(message = "Biography cannot be empty!")
    private String biography;
    @ValidMultipartFile
    private MultipartFile photo;

    public CreateActorBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
