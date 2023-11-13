package com.example.mymovieapp.service;

import com.example.mymovieapp.model.binding.CreateMovieBindingModel;
import com.example.mymovieapp.model.service.MovieServiceModel;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<MovieServiceModel> findAllLimitFive();

    List<MovieServiceModel> getAllMovies();

    void addMovie(CreateMovieBindingModel model) throws IOException;

    MovieServiceModel findMovieById(String id);

    void deleteMovie(String id);

    void editMovie(String id, MovieServiceModel movieServiceModel);
}
