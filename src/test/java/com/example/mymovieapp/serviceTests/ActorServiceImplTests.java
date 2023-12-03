package com.example.mymovieapp.serviceTests;

import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.enums.Genre;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.view.ActorFullViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.repository.ActorRepository;
import com.example.mymovieapp.repository.MovieRepository;
import com.example.mymovieapp.service.ActorService;
import com.example.mymovieapp.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ActorServiceImplTests {
    private static final String PHOTO_URL = "https://img.freepik.com/premium-vector/smooth-color-wave-vectorcurved-lines-rainbow-abstract-multicolored-wave-flow_206325-1410.jpg";
    @Mock
    private ActorRepository mockedActorRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private MovieRepository mockedMovieRepository;

    private Actor testActor;

    private ActorServiceModel testActorServiceModel;

    private ActorService testActorService;

    @BeforeEach
    public void setUp() {
        this.testActor = new Actor();
        testActor.setId(UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b"));
        testActor.setName("ActorName");
        testActor.setBiography("Biography");
        testActor.setPhoto(PHOTO_URL);

        testActorServiceModel = new ActorServiceModel();
        testActorServiceModel.setId(testActor.getId());
        testActorServiceModel.setName(testActor.getName());
        testActorServiceModel.setBiography(testActor.getBiography());
        testActorServiceModel.setPhoto(testActor.getPhoto());

        testActorService = new ActorServiceImpl(this.mockedActorRepository, this.mockedModelMapper, this.mockedMovieRepository);
    }

    @Test
    public void actorService_GetAllActors_ShouldReturnCorrect(){
        when(this.mockedActorRepository
                .findAll())
                .thenReturn(List.of(this.testActor));

        assertEquals("Broken...", testActorService.findAllActors().size(), 1);
    }

    @Test
    public void actorService_GetAllActorsLimit5_ShouldReturnCorrect(){
        when(this.mockedActorRepository
                .findAll())
                .thenReturn(List.of(this.testActor));

        assertEquals("Broken...", testActorService.findAllLimitFive().size(), 1);
    }

    @Test
    public void actorService_AddActor_ShouldReturnCorrect(){
        when(this.mockedActorRepository.saveAndFlush(testActor)).thenReturn(testActor);

        when(this.mockedModelMapper.map(testActorServiceModel, Actor.class)).thenReturn(testActor);
        when(this.mockedModelMapper.map(testActor, ActorServiceModel.class)).thenReturn(testActorServiceModel);

        ActorServiceModel addedActor = testActorService.addActor(testActorServiceModel);

        verify(this.mockedActorRepository, times(1)).saveAndFlush(testActor);

        assertEquals("Broken...", testActor.getName(), addedActor.getName());
    }

    @Test
    public void actorService_GetActorWithID_ShouldReturnCorrect(){
        when(this.mockedActorRepository
                .findById(testActor.getId()))
                .thenReturn(Optional.of(testActor));

        when(this.mockedModelMapper.map(testActor, ActorServiceModel.class)).thenReturn(testActorServiceModel);

        assertEquals("Broken...", testActorService.findActorById(testActor.getId().toString()).getName(),
                testActor.getName());
    }

    @Test
    public void actorService_DeleteActor_ShouldReturnCorrect(){
        // Mock the repository and mapper
        when(this.mockedActorRepository.findById(testActor.getId())).thenReturn(Optional.of(testActor));
        when(this.mockedModelMapper.map(testActor, ActorServiceModel.class)).thenReturn(testActorServiceModel);

        // Call the method under test
        testActorService.deleteActor(testActor.getId().toString());

        // Verify that the delete method was called with the correct argument
        verify(this.mockedActorRepository).delete(any(Actor.class));

        // Verify that the map method was called with the correct arguments
        verify(this.mockedModelMapper).map(any(Actor.class), eq(ActorServiceModel.class));
    }

    @Test
    public void actorService_EditActor_ShouldReturnCorrect(){
        when(this.mockedActorRepository.findById(testActor.getId())).thenReturn(Optional.of(testActor));

        when(this.mockedModelMapper.map(testActor, ActorServiceModel.class)).thenReturn(testActorServiceModel);

        ActorServiceModel actorServiceModelForEdit = new ActorServiceModel();
        testActorServiceModel.setId(testActor.getId());
        testActorServiceModel.setName("Rado");
        testActorServiceModel.setBiography("Alabala");
        testActorServiceModel.setPhoto(testActor.getPhoto());

        ActorServiceModel editedActor = testActorService.editActor(testActor.getId().toString(), actorServiceModelForEdit);

        assertEquals("Broken...", editedActor.getName(), "Rado");
        assertEquals("Broken...", editedActor.getBiography(), "Alabala");
    }

    @Test
    public void actorService_GetAllMoviesForActor_ShouldReturnCorrect(){
        Movie testMovie = new Movie();
        testMovie.setId(UUID.randomUUID());
        testMovie.setActors(List.of(testActor));
        testMovie.setDescription("Desc");
        testMovie.setName("Name");
        testMovie.setMovieMinutes(120);
        testMovie.setPhoto(PHOTO_URL);
        testMovie.setDirector(new Director());
        testMovie.setGenre(Genre.ACTION);
        testMovie.setPremiereDate(LocalDate.now());

        ActorFullViewModel actorForMovie = new ActorFullViewModel();
        actorForMovie.setId(testActor.getId());
        actorForMovie.setName(testActor.getName());
        actorForMovie.setBiography(testActor.getBiography());
        actorForMovie.setPhoto(testActor.getPhoto());

        List<Movie> movies = List.of(testMovie);

        // Mock the repository and mapper
        when(this.mockedMovieRepository.findAllByActorsId(testActor.getId())).thenReturn(movies);
        when(this.mockedModelMapper.map(any(), eq(MovieViewModel.class))).thenAnswer(invocation -> {
            Movie movie = invocation.getArgument(0);
            MovieViewModel movieViewModel = new MovieViewModel();
            movieViewModel.setId(movie.getId());
            movieViewModel.setName(testMovie.getName());
            movieViewModel.setActors(List.of(actorForMovie));
            movieViewModel.setMovieMinutes(testMovie.getMovieMinutes());
            movieViewModel.setDescription(testMovie.getDescription());
            movieViewModel.setPremiereDate(testMovie.getPremiereDate().toString());
            movieViewModel.setGenre(testMovie.getGenre().name());
            movieViewModel.setDirectorName(testMovie.getDirector().getName());
            movieViewModel.setPhoto(testActor.getPhoto());
            return movieViewModel;
        });

        List<MovieViewModel> result = testActorService.getAllMoviesForActor(testActor.getId().toString());

        // Verify the result
        assertEquals("Broken...", movies.size(), result.size());
        for (int i = 0; i < movies.size(); i++) {
            assertEquals("Broken...", movies.get(i).getId(), result.get(i).getId());
            assertEquals("Broken...", movies.get(i).getName(), result.get(i).getName());
            assertEquals("Broken...", movies.get(i).getDescription(), result.get(i).getDescription());
        }
    }

}
