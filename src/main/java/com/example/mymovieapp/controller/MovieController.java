package com.example.mymovieapp.controller;

import com.example.mymovieapp.model.binding.CreateMovieBindingModel;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.service.MovieServiceModel;
import com.example.mymovieapp.model.service.UserServiceModel;
import com.example.mymovieapp.model.view.DirectorViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.service.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController extends BaseController {
    private final UserService userService;
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public MovieController(UserService userService, MovieService movieService, DirectorService directorService, ActorService actorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/movieAdminSettings")
    public ModelAndView getAdminMoviesView() {
        List<MovieViewModel> movies = this.movieService.getAllMovies()
                .stream().map(movieServiceModel -> this.modelMapper.map(movieServiceModel, MovieViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/movies/movieAdminSettings");

        modelAndView.addObject("movies", movies);

        return modelAndView;
    }

    @GetMapping("/adminCreateMovie")
    public ModelAndView addMovieView(@ModelAttribute("createMovie") CreateMovieBindingModel model) {
        List<DirectorServiceModel> directorServiceModels = this.directorService.findAllDirectors();
        List<ActorServiceModel> actorServiceModels = this.actorService.findAllActors();
        ModelAndView modelAndView = new ModelAndView("/movies/adminCreateMovie");

        modelAndView.addObject("actors", actorServiceModels);
        modelAndView.addObject("directors", directorServiceModels);

        return modelAndView;
    }

    @PostMapping("/adminCreateMovie")
    public ModelAndView addMovieConfirm(@ModelAttribute("createMovie") @Valid CreateMovieBindingModel model, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return super.view("/movies/adminCreateMovie");
        }

        this.movieService.addMovie(model);

        return super.redirect("/home");
    }

    @GetMapping("/movieDelete/{id}")
    public ModelAndView deleteMovie(@PathVariable String id) {
        MovieServiceModel movieServiceModel = this.movieService.findMovieById(id);
        MovieViewModel movieModel = this.modelMapper.map(movieServiceModel, MovieViewModel.class);
        ModelAndView modelAndView = new ModelAndView("/movies/movieDelete");

        modelAndView.addObject("movie", movieModel);
        modelAndView.addObject("movieId", id);

        return modelAndView;
    }

    @PostMapping("/movieDelete/{id}")
    public ModelAndView deleteMovieConfirm(@PathVariable String id) {
        this.movieService.deleteMovie(id);

        return super.redirect("/home");
    }

    @GetMapping("/movieEdit/{id}")
    public ModelAndView movieEdit(@PathVariable String id) {
        MovieServiceModel movieServiceModel = this.movieService.findMovieById(id);
        CreateMovieBindingModel model = this.modelMapper.map(movieServiceModel, CreateMovieBindingModel.class);
        List<DirectorServiceModel> directorServiceModels = this.directorService.findAllDirectors();
        List<ActorServiceModel> actorServiceModels = this.actorService.findAllActors();
        ModelAndView modelAndView = new ModelAndView("/movies/movieEdit");

        modelAndView.addObject("actors", actorServiceModels);
        modelAndView.addObject("directors", directorServiceModels);
        modelAndView.addObject("movie", model);
        modelAndView.addObject("movieId", id);

        return modelAndView;
    }

    @PostMapping("/movieEdit/{id}")
    public ModelAndView movieEditConfirm(@PathVariable String id, @ModelAttribute CreateMovieBindingModel model) throws IOException {

        MovieServiceModel movieServiceModel = this.modelMapper.map(model, MovieServiceModel.class);

        movieServiceModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.movieService.editMovie(id, movieServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/movieDetails/{id}")
    public ModelAndView detailsMovie(@PathVariable String id) {
        MovieViewModel movieViewModel = this.modelMapper.map(this.movieService.findMovieById(id), MovieViewModel.class);
        DirectorViewModel directorViewModel = this.modelMapper
                .map(this.directorService.findDirectorByName(movieViewModel.getDirectorName()), DirectorViewModel.class);
        ModelAndView modelAndView = new ModelAndView("/movies/movieDetails");

        modelAndView.addObject("actors", movieViewModel.getActors());
        modelAndView.addObject("movie", movieViewModel);
        modelAndView.addObject("director", directorViewModel);

        return modelAndView;
    }

    @PostMapping("/movieDetails/{id}")
    public ModelAndView movieAddToWatchList(@PathVariable String id, Principal principal) {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());
        List<UUID> moviesIds = userServiceModel.getMovies().stream().map(MovieServiceModel::getId)
                .toList();

        if (!moviesIds.contains(UUID.fromString(id))) {
            MovieServiceModel movieServiceModel = this.modelMapper
                    .map(this.movieService.findMovieById(id), MovieServiceModel.class);

            List<MovieServiceModel> moviesToSet = userServiceModel.getMovies();
            moviesToSet.add(movieServiceModel);
            userServiceModel.setMovies(moviesToSet);

            this.userService.updateMovieWatchList(userServiceModel);
        }

        return super.redirect("/home");
    }

    @GetMapping("/allMovies")
    public ModelAndView movieSeeAll() {
        List<MovieViewModel> allMovies = this.movieService.getAllMovies()
                .stream()
                .map(movieServiceModel -> this.modelMapper.map(movieServiceModel, MovieViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/movies/allMovies");

        modelAndView.addObject("movies", allMovies);

        return modelAndView;
    }

}
