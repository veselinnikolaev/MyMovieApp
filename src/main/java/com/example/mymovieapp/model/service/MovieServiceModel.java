package com.example.mymovieapp.model.service;

import java.time.LocalDate;
import java.util.List;

public class MovieServiceModel extends BaseServiceModel {
    private String name;
    private String description;
    private LocalDate premiereDate;
    private String photo;
    private DirectorServiceModel director;
    private List<ActorServiceModel> actors;
    private String genre;
    private Integer movieMinutes;

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

    public LocalDate getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(LocalDate premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public DirectorServiceModel getDirector() {
        return director;
    }

    public void setDirector(DirectorServiceModel director) {
        this.director = director;
    }

    public List<ActorServiceModel> getActors() {
        return actors;
    }

    public void setActors(List<ActorServiceModel> actors) {
        this.actors = actors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getMovieMinutes() {
        return movieMinutes;
    }

    public void setMovieMinutes(Integer movieMinutes) {
        this.movieMinutes = movieMinutes;
    }
}
