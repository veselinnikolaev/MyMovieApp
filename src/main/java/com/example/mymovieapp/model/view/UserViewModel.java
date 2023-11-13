package com.example.mymovieapp.model.view;

import com.example.mymovieapp.model.entity.Movie;

import java.util.List;
import java.util.UUID;

public class UserViewModel {
    private UUID id;
    private String username;
    private String email;
    private List<Movie> movies;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
