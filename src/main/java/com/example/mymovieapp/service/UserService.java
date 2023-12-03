package com.example.mymovieapp.service;

import com.example.mymovieapp.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    UserServiceModel findUserById(String id);

    List<UserServiceModel> findAllUsers();

    UserServiceModel findUserByUsername(String name);

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel deleteUser(String id);

    UserServiceModel updateMovieWatchList(UserServiceModel userServiceModel);

    UserServiceModel findByEmail(String email);
}
