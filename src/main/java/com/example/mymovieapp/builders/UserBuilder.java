package com.example.mymovieapp.builders;

import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.entity.Role;
import com.example.mymovieapp.model.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserBuilder {
    private UserEntity userEntity;
    private UUID id;
    private String username;
    private String password;
    private String email;
    private List<Movie> movies;
    private Set<Role> authorities;

    public UserBuilder(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withMovies(List<Movie> movies){
        this.movies = movies;
        return this;
    }

    public UserBuilder withAuthorities(Set<Role> authorities){
        this.authorities = authorities;
        return this;
    }

    public UserEntity build(){
        this.userEntity.setId(id);
        this.userEntity.setUsername(username);
        this.userEntity.setPassword(password);
        this.userEntity.setEmail(email);
        this.userEntity.setMovies(movies);
        this.userEntity.setAuthorities(authorities);

        return userEntity;
    }
}
