package com.example.mymovieapp.builders;

import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.enums.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MovieBuilder {
    private final Movie movie;
    private UUID id;
    private String name;
    private String description;
    private LocalDate premiereDate;
    private String photo;
    private Director director;
    private List<Actor> actors;
    private Genre genre;
    private Integer movieMinutes;

    public MovieBuilder(Movie movie) {
        this.movie = movie;
    }

    public MovieBuilder withId(UUID id){
        this.id = id;
        return this;  //By returning the builder each time, we can create a fluent interface.
    }

    public MovieBuilder withName(String name){
        this.name = name;
        return this;
    }

    public MovieBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public MovieBuilder withPremiereDate(LocalDate premiereDate){
        this.premiereDate = premiereDate;
        return this;
    }

    public MovieBuilder withPoster(String photo){
        this.photo = photo;
        return this;
    }

    public MovieBuilder withDirector(Director director){
        this.director = director;
        return this;
    }

    public MovieBuilder withGenre(Genre genre){
        this.genre = genre;
        return this;
    }

    public MovieBuilder withActorsPlayingIn(List<Actor> actors){
        this.actors = actors;
        return this;
    }

    public MovieBuilder withDuration(Integer duration){
        this.movieMinutes = duration;
        return this;
    }

    public Movie build(){
        //Since the builder is in the MovieServiceModel class, we can invoke its private constructor.
        this.movie.setId(this.id);
        this.movie.setName(this.name);
        this.movie.setDirector(this.director);
        this.movie.setDescription(this.description);
        this.movie.setPhoto(this.photo);
        this.movie.setPremiereDate(this.premiereDate);
        this.movie.setActors(this.actors);
        this.movie.setGenre(this.genre);
        this.movie.setMovieMinutes(this.movieMinutes);

        return movie;
    }
}
