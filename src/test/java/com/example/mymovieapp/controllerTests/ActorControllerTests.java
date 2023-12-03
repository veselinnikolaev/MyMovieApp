package com.example.mymovieapp.controllerTests;

import com.example.mymovieapp.controller.ActorController;
import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.enums.UserRoleEnum;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.view.ActorFullViewModel;
import com.example.mymovieapp.model.binding.CreateActorBindingModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.service.ActorService;
import com.example.mymovieapp.service.CloudinaryService;
import com.example.mymovieapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorControllerTests {
    @Mock
    private ActorService actorService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ActorController actorController;

    @Test
    void adminGetActorsView_shouldReturnCorrectViewAndModel() {
        List<ActorServiceModel> actors = new ArrayList<>();
        when(actorService.findAllActors()).thenReturn(actors);

        ModelAndView modelAndView = actorController.adminGetActorsView();

        assertEquals("/actors/actorAdminSettings", modelAndView.getViewName());
        assertEquals(actors, modelAndView.getModel().get("actors"));
    }

    @Test
    void addActor_shouldReturnCorrectViewAndModel() {
        ModelAndView modelAndView = actorController.addActor(new CreateActorBindingModel());

        assertEquals("/actors/adminCreateActor", modelAndView.getViewName());
    }

    @Test
    void deleteActor_shouldReturnCorrectViewAndModel() {
        ActorServiceModel actor = new ActorServiceModel();
        when(actorService.findActorById(anyString())).thenReturn(actor);

        ModelAndView modelAndView = actorController.deleteActor("1");

        assertEquals("/actors/actorDelete", modelAndView.getViewName());
    }

    @Test
    void deleteActorConfirm_shouldRedirectToHome() {
        ModelAndView result = actorController.deleteActorConfirm("1");

        assertEquals("redirect:/home", result.getViewName());
        verify(actorService, times(1)).deleteActor("1");
    }

    @Test
    void actorEdit_shouldReturnCorrectViewAndModel() {
        ActorServiceModel actor = new ActorServiceModel();
        when(actorService.findActorById(anyString())).thenReturn(actor);

        ModelAndView modelAndView = actorController.actorEdit("1");

        assertEquals("/actors/actorEdit", modelAndView.getViewName());
    }

    @Test
    void detailsActor_shouldReturnCorrectViewAndModel() {
        ActorServiceModel actorServiceModel = new ActorServiceModel();
        ActorFullViewModel actorFullViewModel = new ActorFullViewModel();
        when(actorService.findActorById(anyString())).thenReturn(actorServiceModel);
        when(actorService.getAllMoviesForActor(anyString())).thenReturn(new ArrayList<>());
        when(modelMapper.map(actorServiceModel, ActorFullViewModel.class)).thenReturn(actorFullViewModel);

        ModelAndView modelAndView = actorController.detailsActor("1");

        assertEquals("/actors/actorDetails", modelAndView.getViewName());
        assertEquals(actorFullViewModel, modelAndView.getModel().get("actor"));
        assertEquals(new ArrayList<>(), modelAndView.getModel().get("movies"));
    }

    @Test
    void seeAllActors_shouldReturnCorrectViewAndModel() {
        List<ActorFullViewModel> actors = new ArrayList<>();
        when(actorService.findAllActors()).thenReturn(new ArrayList<>());

        ModelAndView modelAndView = actorController.seeAllDirectors();

        assertEquals("/actors/allActors", modelAndView.getViewName());
        assertEquals(new ArrayList<>(), modelAndView.getModel().get("actors"));
    }
}
