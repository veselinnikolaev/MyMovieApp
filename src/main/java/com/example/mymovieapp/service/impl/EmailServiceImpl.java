package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
    public final JavaMailSender emailSender;
    private final String myMovieEmail;

    public EmailServiceImpl(@Qualifier("javaMailSender") JavaMailSender emailSender,
                            @Value("${mail.myMovieApp}") String myMovieEmail) {
        this.emailSender = emailSender;
        this.myMovieEmail = myMovieEmail;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom(myMovieEmail);
        message.setReplyTo(myMovieEmail);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
