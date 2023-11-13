package com.example.mymovieapp.repository;

import com.example.mymovieapp.model.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    List<Director> findAll();

    Optional<Director> findByName(String directorName);
}
