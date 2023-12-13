package com.csye6220.finalprojectesd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.service.MovieService;

@Controller
@RequestMapping("/")
public class HomePageController {
    
//	User newUser = new User("movieadmin", "movieadmin@gmail.com", passwordEncoder.encode("Srishti@99"), UserRole.ADMIN, 4134567890L);
//  newUser.setEnabled(true);
//  userService.saveUser(newUser);
	
	@Autowired
    private MovieService movieService;
    
    @GetMapping
	public String getMoviesPage(Model model) {
		List<Movie> searchResults = movieService.getAllMovies();
		model.addAttribute("movies", searchResults);
	    return "movies";
	}
}
