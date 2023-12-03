package com.example.mymovieapp.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggedInInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Cookie myCookie = new Cookie("JSESSIONID", "If you are looking here, you may want to start working for us.");
        myCookie.setMaxAge(0);
        response.addCookie(myCookie);
    }
}
