package com.example.mymovieapp.config;
import com.example.mymovieapp.model.enums.UserRoleEnum;
import com.example.mymovieapp.repository.UserRepository;
import com.example.mymovieapp.service.impl.MyMovieAppUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf().disable().authorizeHttpRequests(
                // Define which urls are visible by which users
                authorizeRequests -> authorizeRequests
                        // All static resources which are situated in js, images, css are available for anyone
                        .requestMatchers("/js/**", "/css/**").permitAll()
                        // Allow anyone to see the home page, the registration page and the login form
                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
                        .requestMatchers("/users/allUsersAdminSettings", "/users/userDelete/**", "/users/userProfileAdmin/**", "/users/fetch").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/movies/movieAdminSettings", "/movies/adminCreateMovie", "/movies/movieDelete/**", "/movies/movieEdit/**").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/directors/directorAdminSettings", "/directors/adminCreateDirector", "/directors/directorDelete/**", "/directors/directorEdit/**").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/actors/actorAdminSettings", "/actors/adminCreateActor", "/actors/actorDelete/**", "/actors/actorEdit/**").hasRole(UserRoleEnum.ADMIN.name())
                        // all other requests are authenticated.
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> formLogin
                        // redirect here when we access something which is not allowed.
                        // also this is the page where we perform login.
                        .loginPage("/users/login")
                        // The names of the input fields (in our case in auth-login.html)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home")
                        .failureForwardUrl("/users/login/error")
        ).logout(
                logout -> logout
                        // the URL where we should POST something in order to perform the logout
                        .logoutUrl("/users/logout")
                        // where to go when logged out?
                        .logoutSuccessUrl("/")
                        // invalidate the HTTP session
                        .invalidateHttpSession(true)
        ).build();

    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        // This service translates the my_movie_app users and roles
        // to representation which spring security understands.
        return new MyMovieAppUserDetailsService(userRepository);
    }

}
