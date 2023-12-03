package com.csye6220.finalprojectesd.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("currentUser")
    public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}