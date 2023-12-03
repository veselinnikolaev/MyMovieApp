package com.example.mymovieapp.controllerTests;

import com.example.mymovieapp.controller.DirectorController;
import com.example.mymovieapp.model.binding.CreateActorBindingModel;
import com.example.mymovieapp.model.binding.CreateDirectorBindingModel;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.view.ActorFullViewModel;
import com.example.mymovieapp.model.view.DirectorViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.service.CloudinaryService;
import com.example.mymovieapp.service.DirectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DirectorControllerTests {
    @Mock
    private DirectorService directorService;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DirectorController directorController;

    @Test
    void adminGetDirectorsView_shouldReturnCorrectViewAndModel() {
        List<DirectorServiceModel> directors = new ArrayList<>();
        when(directorService.findAllDirectors()).thenReturn(directors);

        ModelAndView modelAndView = directorController.getDirectorAdminSettingsView();

        assertEquals("/directors/directorAdminSettings", modelAndView.getViewName());
        assertEquals(directors, modelAndView.getModel().get("directors"));
    }

    @Test
    void addDirector_shouldReturnCorrectViewAndModel() {
        ModelAndView modelAndView = directorController.addDirector(new CreateDirectorBindingModel());

        assertEquals("directors/adminCreateDirector", modelAndView.getViewName());
    }

    @Test
    void deleteDirector_shouldReturnCorrectViewAndModel() {
        DirectorServiceModel director = new DirectorServiceModel();
        when(directorService.findDirectorById(anyString())).thenReturn(director);

        ModelAndView modelAndView = directorController.deleteDirector("1");

        assertEquals("/directors/directorDelete", modelAndView.getViewName());
    }

    @Test
    void deleteDirectorConfirm_shouldRedirectToHome() {
        ModelAndView result = directorController.deleteDirectorConfirm("1");

        assertEquals("redirect:/home", result.getViewName());
        verify(directorService, times(1)).deleteDirector("1");
    }

    @Test
    void directorEdit_shouldReturnCorrectViewAndModel() {
        DirectorServiceModel director = new DirectorServiceModel();
        when(directorService.findDirectorById(anyString())).thenReturn(director);

        ModelAndView modelAndView = directorController.directorEdit("1");

        assertEquals("/directors/directorEdit", modelAndView.getViewName());
    }

    @Test
    void detailsDirector_shouldReturnCorrectViewAndModel() {
        DirectorServiceModel directorServiceModel = new DirectorServiceModel();
        DirectorViewModel directorViewModel = new DirectorViewModel();
        when(directorService.findDirectorById(anyString())).thenReturn(directorServiceModel);
        when(directorService.getAllMoviesForDirector(anyString())).thenReturn(new ArrayList<>());
        when(modelMapper.map(directorServiceModel, DirectorViewModel.class)).thenReturn(directorViewModel);

        ModelAndView modelAndView = directorController.detailsDirector("1");

        assertEquals("/directors/directorDetails", modelAndView.getViewName());
        assertEquals(directorViewModel, modelAndView.getModel().get("director"));
        assertEquals(new ArrayList<>(), modelAndView.getModel().get("movies"));
    }

    @Test
    void seeAllActors_shouldReturnCorrectViewAndModel() {
        List<DirectorViewModel> directors = new ArrayList<>();
        when(directorService.findAllDirectors()).thenReturn(new ArrayList<>());

        ModelAndView modelAndView = directorController.seeAllDirectors();

        assertEquals("/directors/allDirectors", modelAndView.getViewName());
        assertEquals(new ArrayList<>(), modelAndView.getModel().get("directors"));
    }
}
