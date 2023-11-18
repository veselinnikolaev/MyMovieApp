package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.model.entity.UserEntity;
import com.example.mymovieapp.model.enums.UserRoleEnum;
import com.example.mymovieapp.model.service.RoleServiceModel;
import com.example.mymovieapp.model.service.UserServiceModel;
import com.example.mymovieapp.repository.RoleRepository;
import com.example.mymovieapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.mymovieapp.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return this.userRepository.findById(UUID.fromString(id))
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new Error("Username not found!"));
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new Error("Username not found!"));
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleRepository.findAll().stream().map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                    .collect(Collectors.toSet()));
        } else {
            userServiceModel.setAuthorities(Set.of(this.modelMapper
                    .map(this.roleRepository.findByAuthority(UserRoleEnum.USER), RoleServiceModel.class)));
        }
        UserEntity user = this.modelMapper.map(userServiceModel, UserEntity.class);
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

        this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }
    @Override
    @Transactional
    public void deleteUser(String id) {
        UserEntity user = userRepository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);
        this.userRepository.delete(user);
        modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void updateMovieWatchList(UserServiceModel userServiceModel) {
        UserEntity userToAddMovie = this.modelMapper.map(userServiceModel, UserEntity.class);

        this.modelMapper.map(this.userRepository.saveAndFlush(userToAddMovie), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new Error("Email not found!"));
    }
}
