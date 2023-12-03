package com.example.mymovieapp.controllerTests;

import com.example.mymovieapp.controller.UserController;
import com.example.mymovieapp.model.binding.UserRegisterBindingModel;
import com.example.mymovieapp.model.service.UserServiceModel;
import com.example.mymovieapp.model.view.UserViewModel;
import com.example.mymovieapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        // You can initialize any necessary setup here
    }

    @Test
    void register_shouldReturnRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        UserRegisterBindingModel model = new UserRegisterBindingModel();

        ModelAndView result = userController.register(modelAndView, model);

        assertEquals("users/register", result.getViewName());
    }

    @Test
    void registerConfirm_shouldRedirectToLogin() {
        UserRegisterBindingModel model = new UserRegisterBindingModel();
        when(modelMapper.map(eq(model), eq(UserServiceModel.class))).thenReturn(new UserServiceModel());

        ModelAndView result = userController.registerConfirm(model, mock(BindingResult.class));

        assertEquals("redirect:/users/login", result.getViewName());
    }

    @Test
    void login_shouldReturnLoginPage() {
        ModelAndView result = userController.login();

        assertEquals("users/login", result.getViewName());
    }

    @Test
    void onFailure_shouldReturnLoginPage() {
        ModelAndView result = userController.onFailure("username");

        assertEquals("users/login", result.getViewName());
    }

    @Test
    void userDelete_shouldReturnUserDeletePage() {
        UserViewModel userViewModel = new UserViewModel();
        when(userService.findUserById(anyString())).thenReturn(new UserServiceModel());
        when(modelMapper.map(any(), eq(UserViewModel.class))).thenReturn(userViewModel);

        ModelAndView result = userController.userDelete("1");

        assertEquals("users/userDelete", result.getViewName());
        assertEquals(userViewModel, result.getModel().get("user"));
        assertEquals("1", result.getModel().get("userId"));
    }

    @Test
    void userDeleteConfirm_shouldRedirectToHome() {
        ModelAndView result = userController.userDeleteConfirm("1");

        assertEquals("redirect:/home", result.getViewName());
        verify(userService, times(1)).deleteUser("1");
    }

    @Test
    void seeUserProfile_shouldReturnUserProfilePage() {
        UserViewModel userViewModel = new UserViewModel();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("username");
        when(userService.findUserByUsername("username")).thenReturn(new UserServiceModel());
        when(modelMapper.map(any(), eq(UserViewModel.class))).thenReturn(userViewModel);

        ModelAndView result = userController.seeUserProfile(principal);

        assertEquals("users/userProfile", result.getViewName());
        assertEquals(userViewModel, result.getModel().get("user"));
    }

    @Test
    void seeUserProfileAdmin_shouldReturnUserProfileAdminPage() {
        UserViewModel userViewModel = new UserViewModel();
        when(userService.findUserById("1")).thenReturn(new UserServiceModel());
        when(modelMapper.map(any(), eq(UserViewModel.class))).thenReturn(userViewModel);

        ModelAndView result = userController.seeUserProfileAdmin("1");

        assertEquals("/users/adminProfileDetails", result.getViewName());
        assertEquals(userViewModel, result.getModel().get("user"));
    }
}
