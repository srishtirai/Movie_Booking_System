package com.csye6220.finalprojectesd.config;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;

import com.csye6220.finalprojectesd.service.UserService;
import com.csye6220.finalprojectesd.util.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthenticationProvider authenticationProvider;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder, CustomAuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers("/", "/error", "/signup", "/login", "/movie", "/theater", "/showtime", "*/details/**", "*/search", "/booking/**").permitAll()
                        .requestMatchers("*/add", "*/delete", "/users/**").hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers("*/approve").hasAnyAuthority("ADMIN")
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
                            System.out.println(exception+" "+exception.getMessage());
                            if (exception instanceof BadCredentialsException) {
                                String exceptionMessage = exception.getMessage();

                                if (exceptionMessage.contains("UsernameNotFoundException")) {
                                    errorMessage = "User not found. Sign Up to continue";
                                } else {
                                    errorMessage = "Invalid password";
                                }
                            }	else {
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
        auth
        	.authenticationProvider(authenticationProvider)
        	.userDetailsService(userService)
        	.passwordEncoder(passwordEncoder);
    }
}