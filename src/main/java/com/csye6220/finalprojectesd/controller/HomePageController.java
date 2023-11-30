package com.csye6220.finalprojectesd.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.finalprojectesd.dao.MovieDAO;
import com.csye6220.finalprojectesd.model.Movie;

@Controller
@RequestMapping("/")
public class HomePageController {

    @Autowired
    private MovieDAO movieDAO;
    
    @GetMapping
    public String showHomePage(Model model) {
//    	User newUser = new User("movieadmin", "Srishti@99", UserRole.ADMIN, "movieadmin@gmail.com", 4134567890L);
//		userService.saveUser(newUser);
        List<Movie> movies = movieDAO.getAllMovies();
        model.addAttribute("movies", movies);
        return "homePage";
    }
}
