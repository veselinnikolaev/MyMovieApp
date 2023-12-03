package com.example.mymovieapp.serviceTests;

import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.entity.Role;
import com.example.mymovieapp.model.entity.UserEntity;
import com.example.mymovieapp.model.service.MovieServiceModel;
import com.example.mymovieapp.model.service.RoleServiceModel;
import com.example.mymovieapp.model.service.UserServiceModel;
import com.example.mymovieapp.repository.RoleRepository;
import com.example.mymovieapp.repository.UserRepository;
import com.example.mymovieapp.service.UserService;
import com.example.mymovieapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private RoleRepository mockedRoleRepository;
    @Mock
    private PasswordEncoder mockedPasswordEncoder;

    private UserEntity testUser;

    private UserServiceModel testUserServiceModel;

    private UserService testUserService;

    @BeforeEach
    void setUp(){
        testUser = new UserEntity();
        testUser.setId(UUID.randomUUID());
        testUser.setUsername("Username");
        testUser.setPassword("Password");
        testUser.setEmail("email@email.com");
        testUser.setMovies(List.of(new Movie()));
        testUser.setAuthorities(Set.of(new Role()));

        testUserServiceModel = new UserServiceModel();
        testUserServiceModel.setId(testUser.getId());
        testUserServiceModel.setUsername(testUser.getUsername());
        testUserServiceModel.setPassword(testUser.getPassword());
        testUserServiceModel.setEmail(testUser.getEmail());
        testUserServiceModel.setMovies(List.of(new MovieServiceModel()));
        testUserServiceModel.setAuthorities(Set.of(new RoleServiceModel()));

        testUserService = new UserServiceImpl(mockedUserRepository, mockedModelMapper, mockedRoleRepository, mockedPasswordEncoder);
    }

    @Test
    public void userService_FindUserById_ShouldReturnCorrect(){
        when(this.mockedUserRepository.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));

        when(this.mockedModelMapper.map(testUser, UserServiceModel.class)).thenReturn(testUserServiceModel);

        assertEquals(testUser.getUsername(), testUserService.findUserById(testUser.getId().toString()).getUsername());
    }

    @Test
    public void userService_FindAllUsers_ShouldReturnCorrect(){
        when(this.mockedUserRepository.findAll()).thenReturn(List.of(testUser));

        assertEquals(1, testUserService.findAllUsers().size());
    }

    @Test
    public void userService_FindUserByUsername_ShouldReturnCorrect(){
        when(this.mockedUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.ofNullable(testUser));

        when(this.mockedModelMapper.map(testUser, UserServiceModel.class)).thenReturn(testUserServiceModel);

        assertEquals(testUser.getUsername(), testUserService.findUserByUsername(testUser.getUsername()).getUsername());
    }

    @Test
    public void userService_RegisterUser_ShouldReturnCorrect(){
        when(this.mockedModelMapper.map(testUserServiceModel, UserEntity.class)).thenReturn(testUser);

        when(this.mockedUserRepository.saveAndFlush(testUser)).thenReturn(testUser);

        when(this.mockedModelMapper.map(testUser, UserServiceModel.class)).thenReturn(testUserServiceModel);

        testUserService.registerUser(testUserServiceModel);

        verify(mockedUserRepository, times(1)).saveAndFlush(testUser);
    }

    @Test
    public void userService_DeleteUser_ShouldReturnCorrect(){
        when(this.mockedUserRepository.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));

        when(this.mockedModelMapper.map(testUser, UserServiceModel.class)).thenReturn(testUserServiceModel);

        UserServiceModel deletedUser = testUserService.deleteUser(testUser.getId().toString());

        assertEquals(testUser.getUsername(), deletedUser.getUsername());
    }

    @Test
    public void userService_UpdateMovieWatchList_ShouldReturnCorrect(){
        when(this.mockedModelMapper.map(testUserServiceModel, UserEntity.class)).thenReturn(testUser);

        when(this.mockedUserRepository.saveAndFlush(testUser)).thenReturn(testUser);

        when(this.mockedModelMapper.map(testUser, UserServiceModel.class)).thenReturn(testUserServiceModel);

        UserServiceModel updatedUser = testUserService.updateMovieWatchList(testUserServiceModel);

        assertEquals(testUser.getMovies().size(), updatedUser.getMovies().size());
    }

    @Test
    public void userService_FindByEmail_ShouldReturnCorrect(){
        when(this.mockedUserRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.ofNullable(testUser));

        when(this.mockedModelMapper.map(testUser, UserServiceModel.class)).thenReturn(testUserServiceModel);

        UserServiceModel userByEmail = testUserService.findByEmail(testUser.getEmail());

        assertEquals(testUser.getEmail(), userByEmail.getEmail());
    }
}
