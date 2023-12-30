package com.example.mymovieapp.controller;

import com.example.mymovieapp.model.binding.UserRegisterBindingModel;
import com.example.mymovieapp.model.service.MovieServiceModel;
import com.example.mymovieapp.model.service.UserServiceModel;
import com.example.mymovieapp.model.view.UserViewModel;
import com.example.mymovieapp.service.UserService;
import com.example.mymovieapp.validation.validator.ConfirmPasswordValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = "model") UserRegisterBindingModel model) {
        return super.view("users/register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "model") @Valid UserRegisterBindingModel model
            , BindingResult bindingResult) {
        ConfirmPasswordValidator.getObject(model);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/users/register");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
        this.userService.registerUser(userServiceModel);

        return new ModelAndView("redirect:/users/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return super.view("users/login");
    }

    @GetMapping("/login/error")
    public ModelAndView onFailure(
            @ModelAttribute("username") String username) {
        ModelAndView modelAndView = new ModelAndView("users/login");

        modelAndView.addObject("username", username);
        modelAndView.addObject("bad_credentials", true);

        return modelAndView;
    }

    @GetMapping("/allUsersAdminSettings")
    public ModelAndView adminAllUsersView() {
        List<UserViewModel> allUsers = this.userService.findAllUsers()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/users/allUsersAdminSettings");

        modelAndView.addObject("users", allUsers);

        return modelAndView;
    }

    @GetMapping("/userDelete/{id}")
    public ModelAndView userDelete(@PathVariable String id) {
        UserViewModel user = this.modelMapper.map(this.userService.findUserById(id), UserViewModel.class);
        ModelAndView modelAndView = new ModelAndView("users/userDelete");

        modelAndView.addObject("userId", id);
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value = "/userDelete/{id}", method = RequestMethod.DELETE)
    public ModelAndView userDeleteConfirm(@PathVariable String id) {
        this.userService.deleteUser(id);

        return super.redirect("/home");
    }

    @GetMapping("/userProfile")
    public ModelAndView seeUserProfile(Principal principal) {
        UserViewModel userViewModel = this.modelMapper.map(this.userService.findUserByUsername(principal.getName()), UserViewModel.class);
        ModelAndView modelAndView = new ModelAndView("users/userProfile");

        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("movies", userViewModel.getMovies());

        return modelAndView;
    }

    @GetMapping("/userProfile/{id}")
    public ModelAndView deleteMovieFromWatchList(@PathVariable String id, Principal principal) {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());
        List<UUID> moviesIds = userServiceModel.getMovies().stream().map(MovieServiceModel::getId)
                .toList();

        if (moviesIds.contains(UUID.fromString(id))) {
            List<MovieServiceModel> moviesToSet = userServiceModel.getMovies();

            for (int i = 0; i < moviesToSet.size(); i++) {
                if (moviesToSet.get(i).getId().equals(UUID.fromString(id))) {
                    moviesToSet.remove(i);
                }
            }

            userServiceModel.setMovies(moviesToSet);

            this.userService.updateMovieWatchList(userServiceModel);
        }

        return super.redirect("/home");
    }

    @GetMapping("/userProfileAdmin/{id}")
    public ModelAndView seeUserProfileAdmin(@PathVariable String id) {
        UserViewModel userViewModel = this.modelMapper.map(this.userService.findUserById(id), UserViewModel.class);
        ModelAndView modelAndView = new ModelAndView("/users/adminProfileDetails");

        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("movies", userViewModel.getMovies());

        return modelAndView;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public int fetchCategories() {

        return (int) this.userService.findAllUsers()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class)).count();
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
