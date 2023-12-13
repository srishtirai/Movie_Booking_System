package com.csye6220.finalprojectesd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User userDetails;
        System.out.println(username + " " + password);
        try {
            userDetails = userService.getUserByUsernameOrEmail(username);
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("User not found", ex);
        }
        System.out.println(passwordEncoder.encode(password) + " " + userDetails.getPassword());
        if (!passwordEncoder.encode(password).equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
