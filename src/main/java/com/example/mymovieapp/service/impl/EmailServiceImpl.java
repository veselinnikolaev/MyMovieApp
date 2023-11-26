package com.example.mymovieapp.service.impl;

import com.example.mymovieapp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {

            messageHelper.setTo(to);
            messageHelper.setFrom(myMovieEmail);
            messageHelper.setReplyTo(myMovieEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);

            emailSender.send(messageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
