package com.example.mymovieapp.model.binding;

import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.enums.Genre;
import com.example.mymovieapp.validation.annotation.ValidMultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateMovieBindingModel {
    @NotBlank(message = "Name cannot be empty!")
    private String name;
    @NotBlank(message = "Description cannot be empty!")
    private String description;
    @ValidMultipartFile
    private MultipartFile photo;
    @NotBlank(message = "Premiere date cannot be empty!")
    private String premiereDate;
    @NotNull(message = "You must enter director!")
    private Director director;
    @NotNull(message = "You must enter actors!")
    private List<Actor> actors;
    @NotNull(message = "You must choose genre!")
    private Genre genre;
    @NotNull(message = "You must enter movie duration!")
    @Positive(message = "You must enter POSITIVE movie duration!")
    private Integer movieMinutes;

    public CreateMovieBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Integer getMovieMinutes() {
        return movieMinutes;
    }

    public void setMovieMinutes(Integer movieMinutes) {
        this.movieMinutes = movieMinutes;
    }
}
