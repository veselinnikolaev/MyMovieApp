package com.example.mymovieapp.service;

import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.view.MovieViewModel;

import java.util.List;

public interface ActorService {
    List<ActorServiceModel> findAllLimitFive();

    List<ActorServiceModel> findAllActors();

    ActorServiceModel addActor(ActorServiceModel actorModel);

    ActorServiceModel findActorById(String id);

    void deleteActor(String id);

    ActorServiceModel editActor(String id, ActorServiceModel actorServiceModel);

    List<MovieViewModel> getAllMoviesForActor(String id);
}
