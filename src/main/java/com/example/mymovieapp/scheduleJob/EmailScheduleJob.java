package com.example.mymovieapp.scheduleJob;

import com.example.mymovieapp.model.entity.UserEntity;
import com.example.mymovieapp.service.EmailService;
import com.example.mymovieapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableAsync
@Component
public class EmailScheduleJob {
    private final UserService userService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    public EmailScheduleJob(UserService userService, EmailService emailService, ModelMapper modelMapper) {
        this.userService = userService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @Async
    //@Scheduled(cron = "0 0 12 24 12 ?")
    @Scheduled(fixedRate = 10_000)
    public void christmasScheduleJob() {
        List<UserEntity> users = userService.findAllUsers()
                .stream()
                .map(userServiceModel -> this.modelMapper.map(userServiceModel, UserEntity.class))
                .toList();

        users.forEach(user -> emailService.sendSimpleMessage(
                user.getEmail(), EmailConstants.CHRISTMAS_MESSAGE_TITLE, EmailConstants.CHRISTMAS_MESSAGE_TEXT));
    }
}
