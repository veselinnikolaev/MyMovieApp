package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.repository.ActorRepository;
import com.example.mymovieapp.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.mymovieapp.service.ActorService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;

    public ActorServiceImpl(ActorRepository actorRepository, ModelMapper modelMapper, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<ActorServiceModel> findAllLimitFive() {

        return this.actorRepository.findAll()
                .stream().limit(5L)
                .map(actor -> this.modelMapper.map(actor, ActorServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActorServiceModel> findAllActors() {

        return this.actorRepository.findAll()
                .stream()
                .map(actor -> this.modelMapper.map(actor, ActorServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ActorServiceModel addActor(ActorServiceModel actorServiceModel) {
        Actor actor = this.modelMapper.map(actorServiceModel, Actor.class);

        return this.modelMapper.map(this.actorRepository.saveAndFlush(actor), ActorServiceModel.class);
    }

    @Override
    public ActorServiceModel findActorById(String id) {
        Actor actor = this.actorRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(actor, ActorServiceModel.class);
    }

    @Override
    public void deleteActor(String id) {
        Actor actor = this.actorRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);

        this.actorRepository.delete(actor);

        this.modelMapper.map(actor, ActorServiceModel.class);
    }

    @Override
    public ActorServiceModel editActor(String id, ActorServiceModel actorServiceModel) {
        Actor actor = this.actorRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);

        actor.setName(actorServiceModel.getName());
        actor.setBiography(actorServiceModel.getBiography());
        actor.setPhoto(actorServiceModel.getPhoto());

        return this.modelMapper.map(actor, ActorServiceModel.class);
    }

    @Override
    public List<MovieViewModel> getAllMoviesForActor(String id) {
        return this.movieRepository.findAllByActorsId(UUID.fromString(id)).stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());
    }
}
