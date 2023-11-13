package com.example.mymovieapp.service;

import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.view.MovieViewModel;

import java.util.List;

public interface ActorService {
    List<ActorServiceModel> findAllLimitFive();

    List<ActorServiceModel> findAllActors();

    void addActor(ActorServiceModel actorModel);

    ActorServiceModel findActorById(String id);

    void deleteActor(String id);

    void editActor(String id, ActorServiceModel actorServiceModel);

    List<MovieViewModel> getAllMoviesForActor(String id);
}
