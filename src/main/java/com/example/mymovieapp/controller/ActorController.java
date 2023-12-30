package com.example.mymovieapp.controller;

import com.example.mymovieapp.model.binding.CreateActorBindingModel;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.view.ActorFullViewModel;
import com.example.mymovieapp.model.view.ActorViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.service.ActorService;
import com.example.mymovieapp.service.CloudinaryService;
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
@RequestMapping("/actors")
public class ActorController extends BaseController {
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public ActorController(ActorService actorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/actorAdminSettings")
    public ModelAndView adminGetActorsView() {
        List<ActorViewModel> actors = this.actorService.findAllActors()
                .stream().map(actor -> this.modelMapper.map(actor, ActorViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/actors/actorAdminSettings");

        modelAndView.addObject("actors", actors);

        return modelAndView;
    }

    @GetMapping("/adminCreateActor")
    public ModelAndView addActor(@ModelAttribute("createActor") CreateActorBindingModel model) {
        return super.view("/actors/adminCreateActor");
    }

    @PostMapping("/adminCreateActor")
    public ModelAndView addActorConfirm(@ModelAttribute("createActor") @Valid CreateActorBindingModel model, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return super.view("/actors/adminCreateActor");
        }
        ActorServiceModel actorModel = this.modelMapper.map(model, ActorServiceModel.class);

        actorModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.actorService.addActor(actorModel);

        return super.redirect("/home");
    }

    @GetMapping("/actorDelete/{id}")
    public ModelAndView deleteActor(@PathVariable String id) {
        ActorServiceModel actor = this.actorService.findActorById(id);
        CreateActorBindingModel model = this.modelMapper.map(actor, CreateActorBindingModel.class);
        ModelAndView modelAndView = new ModelAndView("/actors/actorDelete");

        modelAndView.addObject("actor", model);
        modelAndView.addObject("actorId", id);

        return modelAndView;
    }

    @PostMapping("/actorDelete/{id}")
    public ModelAndView deleteActorConfirm(@PathVariable String id) {
        this.actorService.deleteActor(id);

        return super.redirect("/home");
    }

    @GetMapping("/actorEdit/{id}")
    public ModelAndView actorEdit(@PathVariable String id) {
        ActorServiceModel actor = this.actorService.findActorById(id);
        CreateActorBindingModel model = this.modelMapper.map(actor, CreateActorBindingModel.class);
        ModelAndView modelAndView = new ModelAndView("/actors/actorEdit");

        modelAndView.addObject("actor", model);
        modelAndView.addObject("actorId", id);

        return modelAndView;
    }

    @PostMapping("/actorEdit/{id}")
    public ModelAndView editActorConfirm(@PathVariable String id, @ModelAttribute CreateActorBindingModel model) throws IOException {
        ActorServiceModel actorServiceModel = this.modelMapper.map(model, ActorServiceModel.class);

        if(model.getPhoto() == null || model.getPhoto().isEmpty()){
            actorServiceModel.setPhoto(this.actorService.findActorById(id).getPhoto());
        } else {
            actorServiceModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));
        }

        this.actorService.editActor(id, actorServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/actorDetails/{id}")
    public ModelAndView detailsActor(@PathVariable String id) {
        ActorFullViewModel actorFullViewModel = this.modelMapper.map(this.actorService.findActorById(id), ActorFullViewModel.class);

        List<MovieViewModel> actorMovies = this.actorService.getAllMoviesForActor(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/actors/actorDetails");

        modelAndView.addObject("movies", actorMovies);
        modelAndView.addObject("actor", actorFullViewModel);

        return modelAndView;
    }
    @GetMapping("/allActors")
    public ModelAndView seeAllDirectors() {
        List<ActorFullViewModel> actors = this.actorService.findAllActors()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, ActorFullViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/actors/allActors");

        modelAndView.addObject("actors", actors);

        return modelAndView;
    }

}
