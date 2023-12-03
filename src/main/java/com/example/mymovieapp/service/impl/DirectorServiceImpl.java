package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.repository.DirectorRepository;
import com.example.mymovieapp.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.mymovieapp.service.DirectorService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository, ModelMapper modelMapper, MovieRepository movieRepository) {
        this.directorRepository = directorRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<DirectorServiceModel> findAllLimitFive() {

        return directorRepository.findAll()
                .stream().limit(5L)
                .map(director -> this.modelMapper.map(director, DirectorServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorServiceModel> findAllDirectors() {
        return directorRepository.findAll().stream().map(director -> this.modelMapper.map(director, DirectorServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public DirectorServiceModel findDirectorByName(String directorName) {
        Director director = this.directorRepository.findByName(directorName)
                .orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(director, DirectorServiceModel.class);
    }

    @Override
    public void addDirector(DirectorServiceModel directorServiceModel) {
        Director director = this.modelMapper.map(directorServiceModel, Director.class);

        this.modelMapper.map(this.directorRepository.saveAndFlush(director), DirectorServiceModel.class);

    }

    @Override
    public DirectorServiceModel findDirectorById(String id) {
        Director director = this.directorRepository.findById(UUID.fromString(id))
                .orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(director, DirectorServiceModel.class);
    }

    @Override
    public void deleteDirector(String id) {
        Director director = this.directorRepository.findById(UUID.fromString(id))
                .orElseThrow(IllegalArgumentException::new);

        this.directorRepository.delete(director);

        this.modelMapper.map(director, DirectorServiceModel.class);
    }

    @Override
    public DirectorServiceModel editDirector(String id, DirectorServiceModel directorServiceModel) {
        Director director = this.directorRepository.findById(UUID.fromString(id))
                .orElseThrow(IllegalArgumentException::new);

        director.setName(directorServiceModel.getName());
        director.setBiography(directorServiceModel.getBiography());
        director.setPhoto(directorServiceModel.getPhoto());

        return this.modelMapper.map(this.directorRepository.saveAndFlush(director), DirectorServiceModel.class);
    }

    @Override
    public List<MovieViewModel> getAllMoviesForDirector(String id) {
        return this.movieRepository.getAllByDirectorId(UUID.fromString(id))
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());
    }
}
