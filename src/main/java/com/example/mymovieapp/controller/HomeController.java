package com.example.mymovieapp.controller;

import com.example.mymovieapp.model.view.ActorViewModel;
import com.example.mymovieapp.model.view.DirectorViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.service.ActorService;
import com.example.mymovieapp.service.DirectorService;
import com.example.mymovieapp.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    private final ActorService actorService;
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    public HomeController(ActorService actorService, MovieService movieService, DirectorService directorService, ModelMapper modelMapper) {
        this.actorService = actorService;
        this.movieService = movieService;
        this.directorService = directorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        List<ActorViewModel> actorViewModels = this.actorService.findAllLimitFive()
                .stream()
                .map(actorServiceModel -> this.modelMapper.map(actorServiceModel, ActorViewModel.class))
                .collect(Collectors.toList());

        List<MovieViewModel> movieViewModels = this.movieService.findAllLimitFive()
                .stream()
                .map(movieServiceModel -> this.modelMapper.map(movieServiceModel, MovieViewModel.class))
                .collect(Collectors.toList());

        List<DirectorViewModel> directorViewModels = this.directorService.findAllLimitFive()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, DirectorViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/home");

        modelAndView.addObject("movies", movieViewModels);
        modelAndView.addObject("actors", actorViewModels);
        modelAndView.addObject("directors", directorViewModels);

        return modelAndView;
    }
}