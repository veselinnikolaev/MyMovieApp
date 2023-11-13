package com.example.mymovieapp.service;

import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.view.MovieViewModel;

import java.util.List;

public interface DirectorService {
    List<DirectorServiceModel> findAllLimitFive();

    List<DirectorServiceModel> findAllDirectors();

    DirectorServiceModel findDirectorByName(String directorName);

    void addActor(DirectorServiceModel directorModel);

    DirectorServiceModel findDirectorById(String id);

    void deleteDirector(String id);

    void editDirector(String id, DirectorServiceModel directorServiceModel);

    List<MovieViewModel> getAllMoviesForDirector(String id);
}
