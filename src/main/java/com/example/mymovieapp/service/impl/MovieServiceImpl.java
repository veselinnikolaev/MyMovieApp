package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.builders.MovieBuilder;
import com.example.mymovieapp.model.binding.CreateMovieBindingModel;
import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.enums.Genre;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.service.MovieServiceModel;
import com.example.mymovieapp.repository.ActorRepository;
import com.example.mymovieapp.repository.MovieRepository;
import com.example.mymovieapp.service.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.mymovieapp.service.MovieService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final ActorRepository actorRepository;
    private final CloudinaryService cloudinaryService;

    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper, ActorRepository actorRepository, CloudinaryService cloudinaryService) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.actorRepository = actorRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<MovieServiceModel> findAllLimitFive() {

        return this.movieRepository.findAll()
                .stream().limit(5L)
                .map(movie -> this.modelMapper.map(movie, MovieServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieServiceModel> getAllMovies() {
        return this.movieRepository.findAll().stream().map(movie -> this.modelMapper.map(movie, MovieServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void addMovie(CreateMovieBindingModel model) throws IOException {
        MovieServiceModel movieModel = this.modelMapper.map(model, MovieServiceModel.class);

        movieModel.setPremiereDate(LocalDate.parse(model.getPremiereDate()));
        movieModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        Movie movie = this.modelMapper.map(movieModel, Movie.class);

        this.actorRepository.saveAllAndFlush(movie.getActors());

        this.modelMapper.map(this.movieRepository.saveAndFlush(movie), MovieServiceModel.class);
    }

    @Override
    public MovieServiceModel findMovieById(String id) {
        Movie movie = this.movieRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(movie, MovieServiceModel.class);
    }

    @Override
    public void deleteMovie(String id) {
        Movie movie = this.movieRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);
        MovieServiceModel movieServiceModel = this.modelMapper.map(movie, MovieServiceModel.class);
        this.movieRepository.delete(movie);

    }

    @Override
    public void editMovie(String id, MovieServiceModel movieServiceModel) {
        Movie movie = new MovieBuilder(this.movieRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new))
                .withId(UUID.fromString(id))
                .withName(movieServiceModel.getName())
                .withDescription(movieServiceModel.getDescription())
                .withPremiereDate(movieServiceModel.getPremiereDate())
                .withPoster(movieServiceModel.getPhoto())
                .withDirector(this.modelMapper.map(movieServiceModel.getDirector(), Director.class))
                .withActorsPlayingIn(movieServiceModel.getActors()
                        .stream()
                        .map(actorServiceModel -> this.modelMapper.map(actorServiceModel, Actor.class))
                        .collect(Collectors.toList()))
                .withGenre(Genre.valueOf(movieServiceModel.getGenre()))
                .withDuration(movieServiceModel.getMovieMinutes())
                .build();

        Movie movieToReturn = this.movieRepository.saveAndFlush(movie);

        this.modelMapper.map(movieToReturn, MovieServiceModel.class);
    }

    @Override
    public List<ActorServiceModel> getAllActors(String id) {
        return this.movieRepository.findById(UUID.fromString(id)).get().getActors().stream().map(actor -> this.modelMapper.map(actor, ActorServiceModel.class)).toList();
    }
}
