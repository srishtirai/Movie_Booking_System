package com.csye6220.finalprojectesd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.finalprojectesd.model.Genre;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {
    
	@Autowired
    private MovieService movieService;
    
	@GetMapping
	public String getMoviesPage(Model model) {
		List<Movie> searchResults = movieService.getAllMovies();
		model.addAttribute("movies", searchResults);
	    return "movies";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    @GetMapping("/add")
    public String showAddMoviesForm(Model model) {
        model.addAttribute("newMovie", new Movie());
        model.addAttribute("editMode", false); 
        return "addMovie";
    }
    
	@Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    @PostMapping("/add")
    public String addMovie(@ModelAttribute("newMovie") Movie newMovie, Model model) {
    	movieService.saveMovie(newMovie);
        return "redirect:/movie";
    }
    
    @PostMapping("/details")
    public String viewMovieDetails(@RequestParam("movieId") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        List<Review> reviews = movieService.findReviewsByMovie(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            model.addAllAttributes(reviews);
            return "movieDetails";
        } else {
        	model.addAttribute("movie", movie);
            return "redirect:/movie";
        }
    }
    
    @GetMapping("/search")
    public String searchMovies(@RequestParam String keyword, @RequestParam Genre genre, Model model) {
    	List<Movie> searchResults;
    	if(genre == null) {
    		searchResults = movieService.findByMovieName(keyword);
    	} else {
    		searchResults = movieService.findByGenre(genre);
    	}
        model.addAttribute("movies", searchResults);
	    return "movies";
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    @PostMapping("/edit")
    public String showEditMovieForm(@RequestParam("movieId") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);

        if (movie != null) {
            model.addAttribute("newMovie", movie);
            model.addAttribute("editMode", true); 
            return "addMovie";
        } else {
            return "redirect:/movie";
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    @PostMapping("/editSave")
    public String editMovie(@ModelAttribute Movie editedMovie, Model model) {
        movieService.updateMovie(editedMovie);
        return "redirect:/movie";
    }

    @Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    @PostMapping("/delete")
    public String deleteMovie(@RequestParam("movieId") Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

}
