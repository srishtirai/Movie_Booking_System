package com.csye6220.finalprojectesd.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.finalprojectesd.model.Movie;
//import com.csye6220.finalprojectesd.model.User;
//import com.csye6220.finalprojectesd.model.UserRole;
import com.csye6220.finalprojectesd.service.MovieService;
//import com.csye6220.finalprojectesd.service.UserService;

@Controller
@RequestMapping("/")
public class HomePageController {

    @Autowired
    private MovieService movieService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public String showHomePage(Model model) {
//    	User newUser = new User("movieadmin", "movieadmin@gmail.com", passwordEncoder.encode("Srishti@99"), UserRole.ADMIN, 4134567890L);
//		userService.saveUser(newUser);
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "homePage";
    }
}
