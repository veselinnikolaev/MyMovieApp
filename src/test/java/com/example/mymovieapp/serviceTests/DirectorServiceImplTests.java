package com.example.mymovieapp.serviceTests;

import com.example.mymovieapp.model.entity.Actor;
import com.example.mymovieapp.model.entity.Director;
import com.example.mymovieapp.model.entity.Movie;
import com.example.mymovieapp.model.enums.Genre;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.view.ActorFullViewModel;
import com.example.mymovieapp.model.view.DirectorViewModel;
import com.example.mymovieapp.model.view.MovieViewModel;
import com.example.mymovieapp.repository.DirectorRepository;
import com.example.mymovieapp.repository.MovieRepository;
import com.example.mymovieapp.service.DirectorService;
import com.example.mymovieapp.service.impl.DirectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceImplTests {
    private static final String PHOTO_URL = "https://img.freepik.com/premium-vector/smooth-color-wave-vectorcurved-lines-rainbow-abstract-multicolored-wave-flow_206325-1410.jpg";
    @Mock
    private DirectorRepository mockedDirectorRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private MovieRepository mockedMovieRepository;

    private Director testDirector;

    private DirectorServiceModel testDirectorServiceModel;

    private DirectorService testDirectorService;

    @BeforeEach
    public void setUp() {
        this.testDirector = new Director();
        testDirector.setId(UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b"));
        testDirector.setName("DirectorName");
        testDirector.setBiography("Biography");
        testDirector.setPhoto(PHOTO_URL);

        testDirectorServiceModel = new DirectorServiceModel();
        testDirectorServiceModel.setId(testDirector.getId());
        testDirectorServiceModel.setName(testDirector.getName());
        testDirectorServiceModel.setBiography(testDirector.getBiography());
        testDirectorServiceModel.setPhoto(testDirector.getPhoto());

        testDirectorService = new DirectorServiceImpl(mockedDirectorRepository, mockedModelMapper, mockedMovieRepository);
    }

    @Test
    public void directorService_GetAllDirectors_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository
                .findAll())
                .thenReturn(List.of(this.testDirector));

        DirectorService directorService = new DirectorServiceImpl(this.mockedDirectorRepository, new ModelMapper(), mockedMovieRepository);

        assertEquals("Broken...", directorService.findAllDirectors().size(), 1);
    }

    @Test
    public void directorService_FindDirectorByName_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository.findByName("DirectorName")).thenReturn(Optional.ofNullable(testDirector));

        when(this.mockedModelMapper.map(testDirector, DirectorServiceModel.class)).thenReturn(testDirectorServiceModel);

        assertEquals("Broken...", testDirectorServiceModel.getName(), testDirectorService.findDirectorByName(testDirector.getName()).getName());
    }

    @Test
    public void directorService_GetAllDirectorsLimit5_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository
                .findAll())
                .thenReturn(List.of(this.testDirector));

        assertEquals("Broken...", testDirectorService.findAllLimitFive().size(), 1);
    }

    @Test
    public void directorService_AddDirector_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository.saveAndFlush(testDirector)).thenReturn(testDirector);

        when(this.mockedModelMapper.map(testDirectorServiceModel, Director.class)).thenReturn(testDirector);
        when(this.mockedModelMapper.map(testDirector, DirectorServiceModel.class)).thenReturn(testDirectorServiceModel);

        testDirectorService.addDirector(testDirectorServiceModel);

        verify(this.mockedDirectorRepository).saveAndFlush(any(Director.class));

        when(this.mockedDirectorRepository
                .findAll())
                .thenReturn(List.of(this.testDirector, testDirector));

        assertEquals("Broken...", testDirectorService.findAllDirectors().size(), 2);
    }

    @Test
    public void directorService_GetDirectorWithID_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository
                .findById(UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b")))
                .thenReturn(Optional.of(testDirector));

        when(this.mockedModelMapper.map(testDirector, DirectorServiceModel.class)).thenReturn(testDirectorServiceModel);

        assertEquals("Broken...", testDirectorService.findDirectorById("0f14d0ab-9605-4a62-a9e4-5ed26688389b").getName(),
                testDirector.getName());
    }

    @Test
    public void directorService_DeleteDirector_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository.findById(testDirector.getId())).thenReturn(Optional.of(testDirector));
        when(this.mockedModelMapper.map(testDirector, DirectorServiceModel.class)).thenReturn(testDirectorServiceModel);

        // Call the method under test
        testDirectorService.deleteDirector(testDirector.getId().toString());

        // Verify that the delete method was called with the correct argument
        verify(this.mockedDirectorRepository).delete(any(Director.class));

        // Verify that the map method was called with the correct arguments
        verify(this.mockedModelMapper).map(any(Director.class), eq(DirectorServiceModel.class));
    }

    @Test
    public void directorService_EditDirector_ShouldReturnCorrect(){
        when(this.mockedDirectorRepository.findById(testDirector.getId())).thenReturn(Optional.of(testDirector));

        when(mockedDirectorRepository.saveAndFlush(testDirector)).thenReturn(testDirector);

        when(this.mockedModelMapper.map(testDirector, DirectorServiceModel.class)).thenReturn(testDirectorServiceModel);

        DirectorServiceModel directorServiceModelForEdit = new DirectorServiceModel();
        testDirectorServiceModel.setId(testDirector.getId());
        testDirectorServiceModel.setName("Rado");
        testDirectorServiceModel.setBiography("Alabala");
        testDirectorServiceModel.setPhoto(testDirector.getPhoto());

        DirectorServiceModel editedDirector = testDirectorService.editDirector(testDirector.getId().toString(), directorServiceModelForEdit);

        assertEquals("Broken...", editedDirector.getName(), "Rado");
        assertEquals("Broken...", editedDirector.getBiography(), "Alabala");
    }

    @Test
    public void directorService_GetAllMoviesForDirector_ShouldReturnCorrect(){
        Movie testMovie = new Movie();
        testMovie.setId(UUID.randomUUID());
        testMovie.setActors(List.of(new Actor()));
        testMovie.setDescription("Desc");
        testMovie.setName("Name");
        testMovie.setMovieMinutes(120);
        testMovie.setPhoto(PHOTO_URL);
        testMovie.setDirector(testDirector);
        testMovie.setGenre(Genre.ACTION);
        testMovie.setPremiereDate(LocalDate.now());

        DirectorViewModel directorForMovie = new DirectorViewModel();
        directorForMovie.setId(testDirector.getId());
        directorForMovie.setName(testDirector.getName());
        directorForMovie.setBiography(testDirector.getBiography());
        directorForMovie.setPhoto(testDirector.getPhoto());

        List<Movie> movies = List.of(testMovie);

        // Mock the repository and mapper
        when(this.mockedMovieRepository.getAllByDirectorId(testDirector.getId())).thenReturn(movies);
        when(this.mockedModelMapper.map(any(), eq(MovieViewModel.class))).thenAnswer(invocation -> {
            Movie movie = invocation.getArgument(0);
            MovieViewModel movieViewModel = new MovieViewModel();
            movieViewModel.setId(movie.getId());
            movieViewModel.setName(testMovie.getName());
            movieViewModel.setActors(List.of(new ActorFullViewModel()));
            movieViewModel.setMovieMinutes(testMovie.getMovieMinutes());
            movieViewModel.setDescription(testMovie.getDescription());
            movieViewModel.setPremiereDate(testMovie.getPremiereDate().toString());
            movieViewModel.setGenre(testMovie.getGenre().name());
            movieViewModel.setDirectorName(testMovie.getDirector().getName());
            movieViewModel.setPhoto(testMovie.getPhoto());
            return movieViewModel;
        });
        // Call the method under test
        List<MovieViewModel> result = testDirectorService.getAllMoviesForDirector(testDirector.getId().toString());

        // Verify the result
        assertEquals("Broken...", movies.size(), result.size());
        for (int i = 0; i < movies.size(); i++) {
            assertEquals("Broken...", movies.get(i).getId(), result.get(i).getId());
            assertEquals("Broken...", movies.get(i).getName(), result.get(i).getName());
            assertEquals("Broken...", movies.get(i).getDescription(), result.get(i).getDescription());
        }
    }

}