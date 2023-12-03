package com.example.mymovieapp;

import com.cloudinary.Cloudinary;
import com.example.mymovieapp.service.impl.CloudinaryServiceImpl;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyMovieAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMovieAppApplication.class, args);
    }
}
