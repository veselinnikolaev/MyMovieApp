package com.example.mymovieapp.config;

import com.example.mymovieapp.interceptors.LoggedInInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final LoggedInInterceptor loggedInInterceptor;

    public InterceptorConfig(LoggedInInterceptor loggedInInterceptor) {
        this.loggedInInterceptor = loggedInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.loggedInInterceptor);
    }
}
