package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.model.entity.Role;
import com.example.mymovieapp.model.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import com.example.mymovieapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class MyMovieAppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyMovieAppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static UserDetails map(UserEntity userEntity) {
        return User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getAuthorities().stream().map(MyMovieAppUserDetailsService::map).collect(Collectors.toSet()))
                .build();
    }

    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role.getAuthority().name()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(MyMovieAppUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }
}
