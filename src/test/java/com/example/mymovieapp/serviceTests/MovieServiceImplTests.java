package com.example.mymovieapp.serviceTests;

import com.example.mymovieapp.model.binding.CreateMovieBindingModel;
import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.enums.Genre;
import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.service.MovieServiceModel;
import com.example.mymovieapp.repository.ActorRepository;
import com.example.mymovieapp.repository.MovieRepository;
import com.example.mymovieapp.service.CloudinaryService;
import com.example.mymovieapp.service.MovieService;
import com.example.mymovieapp.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTests {
    private static final String PHOTO_URL = "https://img.freepik.com/premium-vector/smooth-color-wave-vectorcurved-lines-rainbow-abstract-multicolored-wave-flow_206325-1410.jpg";
    @Mock
    private MovieRepository mockedMovieRepository;

    @Mock
    private ActorRepository mockedActorRepository;

    @Mock
    private CloudinaryService mockedCloudinaryService;

    @Mock
    private ModelMapper mockedModelMapper;

    private Movie testMovie;

    private MovieServiceModel testMovieServiceModel;

    private MovieService testMovieService;

    @BeforeEach
    void setUp() {
        this.testMovie = new Movie();
        testMovie.setId(UUID.randomUUID());
        testMovie.setName("Name");
        testMovie.setActors(List.of(new Actor()));
        testMovie.setMovieMinutes(120);
        testMovie.setDescription("Desc");
        testMovie.setPremiereDate(LocalDate.now());
        testMovie.setGenre(Genre.ACTION);
        testMovie.setDirector(new Director());
        testMovie.setPhoto(PHOTO_URL);

        testMovieServiceModel = new MovieServiceModel();
        testMovieServiceModel.setId(testMovie.getId());
        testMovieServiceModel.setName(testMovie.getName());
        testMovieServiceModel.setDescription(testMovie.getDescription());
        testMovieServiceModel.setMovieMinutes(testMovie.getMovieMinutes());
        testMovieServiceModel.setDirector(new DirectorServiceModel());
        testMovieServiceModel.setGenre(testMovie.getGenre().name());
        testMovieServiceModel.setPhoto(testMovie.getPhoto());
        testMovieServiceModel.setActors(List.of(new ActorServiceModel()));
        testMovieServiceModel.setPremiereDate(testMovie.getPremiereDate());

        testMovieService = new MovieServiceImpl(mockedMovieRepository, mockedModelMapper, mockedActorRepository, mockedCloudinaryService);
    }

    @Test
    void findAllLimitFive_ShouldReturnLimitedMovies() {
        // Arrange
        when(mockedMovieRepository.findAll()).thenReturn(List.of(testMovie));

        // Assert
        assertEquals(1, testMovieService.findAllLimitFive().size());
    }

    @Test
    void getAllMovies_ShouldReturnAllMovies() {
        // Arrange
        when(mockedMovieRepository.findAll()).thenReturn(List.of(testMovie));

        // Assert
        assertEquals(1, testMovieService.getAllMovies().size());
    }

    @Test
    void addMovie_ShouldAddMovie() throws IOException {
        // Arrange
        CreateMovieBindingModel model = new CreateMovieBindingModel();
        model.setPremiereDate("2023-01-01");
        model.setPhoto(new MockMultipartFile("photo", "test.jpg", "image/jpeg", "test photo".getBytes()));

        Movie movie = new Movie();
        movie.setActors(Arrays.asList(new Actor(), new Actor()));

        when(mockedModelMapper.map(model, MovieServiceModel.class)).thenReturn(new MovieServiceModel());
        when(mockedModelMapper.map(any(), eq(Movie.class))).thenReturn(movie);
        when(mockedCloudinaryService.uploadImage(any())).thenReturn("image_url");
        when(mockedActorRepository.saveAllAndFlush(any())).thenReturn(Arrays.asList(new Actor(), new Actor()));
        when(mockedMovieRepository.saveAndFlush(any())).thenReturn(new Movie());


        // Act
        testMovieService.addMovie(model);

        // Assert
        verify(mockedMovieRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void findMovieById_ShouldReturnMovie() {
        when(mockedMovieRepository.findById(testMovie.getId())).thenReturn(Optional.ofNullable(testMovie));

        when(mockedModelMapper.map(testMovie, MovieServiceModel.class)).thenReturn(testMovieServiceModel);

        // Assert
        assertEquals(testMovieServiceModel.getName(), testMovieService.findMovieById(testMovie.getId().toString()).getName());
    }

    @Test
    void deleteMovie_ShouldDeleteMovie() {
        when(mockedMovieRepository.findById(testMovie.getId())).thenReturn(Optional.of(testMovie));

        when(mockedModelMapper.map(testMovie, MovieServiceModel.class)).thenReturn(testMovieServiceModel);

        testMovieService.deleteMovie(testMovie.getId().toString());

        // Assert
        verify(mockedMovieRepository, times(1)).delete(any());
    }

    @Test
    void editMovie_ShouldEditMovie() {
        when(mockedMovieRepository.findById(testMovie.getId())).thenReturn(Optional.of(testMovie));
        when(mockedMovieRepository.saveAndFlush(testMovie)).thenReturn(testMovie);

        when(mockedModelMapper.map(any(Movie.class), eq(MovieServiceModel.class))).thenReturn(testMovieServiceModel);

        when(mockedModelMapper.map(testMovieServiceModel.getDirector(), Director.class)).thenReturn(testMovie.getDirector());
        when(mockedModelMapper.map(any(ActorServiceModel.class), eq(Actor.class))).thenReturn(testMovie.getActors().get(0));

        // Act
        testMovieService.editMovie(testMovie.getId().toString(), testMovieServiceModel);

        // Assert
        verify(mockedMovieRepository, times(1)).saveAndFlush(testMovie);

        assertEquals(testMovie.getName(), testMovieServiceModel.getName());
    }

    @Test
    void getAllActors_ShouldReturnAllActors() {
        // Arrange
        when(mockedMovieRepository.findById(testMovie.getId())).thenReturn(Optional.of(testMovie));

        // Assert
        assertEquals(1, testMovieService.getAllActors(testMovie.getId().toString()).size());
    }
}

