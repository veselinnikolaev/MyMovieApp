package com.example.mymovieapp.schedulejob.cloudinary;

import com.example.mymovieapp.model.service.ActorServiceModel;
import com.example.mymovieapp.model.service.DirectorServiceModel;
import com.example.mymovieapp.model.service.MovieServiceModel;
import com.example.mymovieapp.service.ActorService;
import com.example.mymovieapp.service.DirectorService;
import com.example.mymovieapp.service.MovieService;
import com.example.mymovieapp.service.impl.CloudinaryServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class CloudinaryScheduleJob {
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final CloudinaryServiceImpl cloudinaryService;

    public CloudinaryScheduleJob(MovieService movieService, DirectorService directorService, ActorService actorService, CloudinaryServiceImpl cloudinaryService) {
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.cloudinaryService = cloudinaryService;
    }

    //@Scheduled(cron = "0 0 0/12 * * *")
    @Scheduled(fixedRate = 10_000)
    public void deleteUnusedImages(){
        List<String> movieImageUrls = movieService.getAllMovies().stream().map(MovieServiceModel::getPhoto).toList();
        List<String> directorImageUrls = directorService.findAllDirectors().stream().map(DirectorServiceModel::getPhoto).toList();
        List<String> actorImageUrls = actorService.findAllActors().stream().map(ActorServiceModel::getPhoto).toList();


        HashSet<String> imageUrls = new HashSet<>();
        imageUrls.addAll(movieImageUrls);
        imageUrls.addAll(directorImageUrls);
        imageUrls.addAll(actorImageUrls);

        List<String> imageUrlsToDelete = cloudinaryService.findAll();
        imageUrlsToDelete.removeAll(imageUrls);

        if(imageUrlsToDelete.size() > 0){
            imageUrlsToDelete.forEach(cloudinaryService::deleteImage);

            System.out.println(imageUrlsToDelete.size() + " images deleted");
            System.out.println(String.join("\n", imageUrlsToDelete));
        } else {
            System.out.println("No images to delete.");
        }
    }
}
