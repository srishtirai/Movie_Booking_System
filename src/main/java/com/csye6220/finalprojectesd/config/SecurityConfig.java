package com.csye6220.finalprojectesd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;

import com.csye6220.finalprojectesd.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers("/", "/error", "/signup", "/login", "/movie", "/theater", "/showtime", "*/details", "/booking/**", "*/search").permitAll()
                        .requestMatchers("*/add", "*/delete", "/users/**").hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers("*/approve").hasAnyAuthority("ADMIN")
                        .requestMatchers("/booking/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                        .anyRequest().authenticated()
        )
                .httpBasic(Customizer.withDefaults())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler((request, response, exception) -> {
                            String errorMessage;
                            if (exception instanceof UsernameNotFoundException) {
                                errorMessage = "User not found";
                            } else if (exception instanceof BadCredentialsException) {
                                errorMessage = "Invalid password";
                            } else {
                                errorMessage = "Authentication failed";
                            }

                            request.getSession().setAttribute("error", errorMessage);
                            new DefaultRedirectStrategy().sendRedirect(request, response, "/login?error=true");
                        })
                )
                .logout((logout) -> logout
                        .permitAll()
                        .logoutUrl("/")
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}