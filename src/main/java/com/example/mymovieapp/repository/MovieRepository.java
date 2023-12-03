package com.example.mymovieapp.repository;

import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    List<Movie> findAll();

    List<Movie> getAllByDirectorId(UUID id);

    List<Movie> findAllByActorsId(UUID id);
}
