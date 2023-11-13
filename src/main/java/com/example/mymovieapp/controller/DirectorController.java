package com.example.mymovieapp.controller;

import com.example.mymovieapp.model.binding.CreateDirectorBindingModel;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.view.DirectorViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.service.CloudinaryService;
import com.example.mymovieapp.service.DirectorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/directors")
public class DirectorController extends BaseController {
    private final DirectorService directorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public DirectorController(DirectorService directorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.directorService = directorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/directorAdminSettings")
    public ModelAndView getDirectorAdminSettingsView() {
        List<DirectorViewModel> directors = this.directorService.findAllDirectors()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, DirectorViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/directors/directorAdminSettings");

        modelAndView.addObject("directors", directors);

        return modelAndView;
    }

    @GetMapping("/adminCreateDirector")
    public ModelAndView addDirector(@ModelAttribute("createDirector") CreateDirectorBindingModel model) {
        return super.view("directors/adminCreateDirector");
    }

    @PostMapping("/adminCreateDirector")
    public ModelAndView addDirectorConfirm(@ModelAttribute("createDirector") @Valid CreateDirectorBindingModel model, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return super.view("/directors/adminCreateDirector");
        }
        DirectorServiceModel directorModel = this.modelMapper.map(model, DirectorServiceModel.class);

        directorModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.directorService.addActor(directorModel);

        return super.redirect("/home");
    }

    @GetMapping("/directorDelete/{id}")
    public ModelAndView deleteDirector(@PathVariable String id) {
        DirectorServiceModel director = this.directorService.findDirectorById(id);
        CreateDirectorBindingModel model = this.modelMapper.map(director, CreateDirectorBindingModel.class);
        ModelAndView modelAndView = new ModelAndView("/directors/directorDelete");

        modelAndView.addObject("director", model);
        modelAndView.addObject("directorId", id);

        return modelAndView;
    }

    @PostMapping("/directorDelete/{id}")
    public ModelAndView deleteDirectorConfirm(@PathVariable String id) {
        this.directorService.deleteDirector(id);

        return super.redirect("/home");
    }

    @GetMapping("/directorEdit/{id}")
    public ModelAndView directorEdit(@PathVariable String id) {
        DirectorServiceModel director = this.directorService.findDirectorById(id);
        CreateDirectorBindingModel model = this.modelMapper.map(director, CreateDirectorBindingModel.class);
        ModelAndView modelAndView = new ModelAndView("/directors/directorEdit");

        modelAndView.addObject("director", model);
        modelAndView.addObject("directorId", id);

        return modelAndView;
    }

    @PostMapping("/directorEdit/{id}")
    public ModelAndView directorEditConfirm(@PathVariable String id, @ModelAttribute CreateDirectorBindingModel model) throws IOException {
        DirectorServiceModel directorServiceModel = this.modelMapper.map(model, DirectorServiceModel.class);
        directorServiceModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.directorService.editDirector(id, directorServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/directorDetails/{id}")
    public ModelAndView detailsDirector(@PathVariable String id) {
        DirectorViewModel directorFullViewModel = this.modelMapper.map(this.directorService.findDirectorById(id), DirectorViewModel.class);

        List<MovieViewModel> actorMovies = this.directorService.getAllMoviesForDirector(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/directors/directorDetails");

        modelAndView.addObject("movies", actorMovies);
        modelAndView.addObject("director", directorFullViewModel);

        return modelAndView;
    }

    @GetMapping("/allDirectors")
    public ModelAndView seeAllDirectors() {
        List<DirectorViewModel> directors = this.directorService.findAllDirectors()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, DirectorViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/directors/allDirectors");

        modelAndView.addObject("directors", directors);

        return modelAndView;
    }
}