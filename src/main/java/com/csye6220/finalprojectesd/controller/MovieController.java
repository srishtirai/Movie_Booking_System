package com.csye6220.finalprojectesd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/add")
    public String showAddMoviesForm(Model model) {
    	Movie newMovie = new Movie();
        model.addAttribute("newMovie", newMovie);
        return "addMovie";
    }
    
    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie newMovie, Model model) {
    	movieService.saveMovie(newMovie);
        return "redirect:/movie/list";
    }
    
    @GetMapping("/list")
    public String listMovies(Model model) {
    	 List<Movie> movies = movieService.getAllMovies();
         model.addAttribute("movies", movies);
        return "movieList";
    }
    
    @GetMapping("/{id}")
    public String viewMovieDetails(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        List<Review> reviews = movieService.findReviewsByMovie(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            model.addAllAttributes(reviews);
            return "movieDetails";
        } else {
        	model.addAttribute("movie", movie);
            return "redirect:/movie/list";
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
	    return "movieList";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);

        if (movie != null) {
            model.addAttribute("editedMovie", movie);
            return "editMovie";
        } else {
            return "redirect:/movie/list";
        }
    }

    @PostMapping("/edit")
    public String editMovie(@ModelAttribute Movie editedMovie, Model model) {
        movieService.updateMovie(editedMovie);
        return "redirect:/movie/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movie/list";
    }

}
