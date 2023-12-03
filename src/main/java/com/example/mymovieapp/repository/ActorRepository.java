package com.example.mymovieapp.repository;

import com.example.mymovieapp.model.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {

    List<Actor> findAll();

    Actor findByName(String name);
}
