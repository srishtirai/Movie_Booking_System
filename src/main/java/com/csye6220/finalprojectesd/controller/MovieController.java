package com.csye6220.finalprojectesd.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.csye6220.finalprojectesd.model.Genre;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.service.MovieService;
import com.csye6220.finalprojectesd.service.ShowtimeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/movie")
public class MovieController {
    
	@Autowired
    private MovieService movieService;
	
	@Autowired
	private ShowtimeService showtimeService;
    
	@GetMapping
	public String getMoviesPage(Model model) {
		List<Movie> searchResults = movieService.getAllMovies();
		model.addAttribute("movies", searchResults);
	    return "movies";
	}
	
    @GetMapping("/add")
    public String showAddMoviesForm(Model model) {
        model.addAttribute("newMovie", new Movie());
        model.addAttribute("editMode", false); 
        return "addMovie";
    }
    
    @PostMapping("/add")
    public String addMovie(@ModelAttribute("newMovie") Movie newMovie, Model model) {
    	movieService.saveMovie(newMovie);
        return "redirect:/movie";
    }
    
    @GetMapping("/details")
    public String viewMovieDetails(@RequestParam("movieId") Long id, Model model, HttpServletRequest request) {
    	
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            List<Review> reviews = movieService.findReviewsByMovie(id);
            List<Showtime> showtimes = showtimeService.getAllShowtimesByMovie(movie);
            
            model.addAttribute("movie", movie);
            model.addAttribute("showtimes", showtimes);
            model.addAttribute("review", reviews);
            
            Map<String, ?> flashAttributes = RequestContextUtils.getInputFlashMap(request);
            if (flashAttributes != null) {
                model.mergeAttributes(flashAttributes);
            }
            
            return "movieDetails";
        } else {
        	model.addAttribute("movie", movie);
            return "redirect:/movie";
        }
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "genre", required = false) Genre genre,
            Model model) {
    	
    	List<Movie> searchResults;
    	
    	if(!name.equals("") && genre == null) {
    		searchResults = movieService.findByMovieName(name);
    	} else if(genre != null && name.equals("")) {
    		searchResults = movieService.findByGenre(genre);
    	} else if(!name.equals("") && genre != null) {
    		searchResults = movieService.findByGenreAndName(genre, name);
    	} else {
    		searchResults = movieService.getAllMovies();
    	}
    	
    	model.addAttribute("keyword", name);
    	model.addAttribute("genre", genre);
        model.addAttribute("movies", searchResults);
	    return "movies";
    }
    
    @PostMapping("/edit")
    public String showEditMovieForm(@RequestParam("movieId") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        
        if (movie != null) {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = movie.getReleaseDate().format(formatter);
            model.addAttribute("formattedReleaseDate", formattedDate);
        	
            model.addAttribute("newMovie", movie);
            model.addAttribute("editMode", true); 
            return "addMovie";
        } else {
            return "redirect:/movie";
        }
    }

    @PostMapping("/editSave")
    public String editMovie(@ModelAttribute Movie editedMovie, Model model) {
    	movieService.updateMovie(editedMovie);
        return "redirect:/movie";
    }
    
    @PostMapping("/delete")
    public String deleteMovie(@RequestParam("movieId") Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

}
