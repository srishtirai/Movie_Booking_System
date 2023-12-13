package com.csye6220.finalprojectesd.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.Theater;
import com.csye6220.finalprojectesd.service.MovieService;
import com.csye6220.finalprojectesd.service.ShowtimeService;
import com.csye6220.finalprojectesd.service.TheaterService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/showtime")
public class ShowtimeController {
    
	@Autowired
	private ShowtimeService showtimeService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private TheaterService theaterService;
	
	@GetMapping
    public String showHomePage(Model model) {
        return "homePage";
    }
	
	@GetMapping("/add/{type}/{id}")
	public String showAddShowtimesForm(@PathVariable String type, @PathVariable Long id, Model model) {
	    Showtime showtime = new Showtime();

	    if ("theater".equals(type)) {
	        showtime.setTheater(theaterService.getTheaterById(id));
	        model.addAttribute("showTimeFor", "theater");
	    } else if ("movie".equals(type)) {
	        showtime.setMovie(movieService.getMovieById(id));
	        model.addAttribute("showTimeFor", "movie");
	    }

	    model.addAttribute("newshowtime", showtime);
	    model.addAttribute("theaters", theaterService.getAllTheaters());
	    model.addAttribute("movies", movieService.getAllMovies());
	    model.addAttribute("editMode", false);

	    return "addShowtime";
	}
    
	@PostMapping("/add/{type}")
	public String addShowtime(@Valid @ModelAttribute("newshowtime") Showtime newshowtime,
			BindingResult bindingResult, 
			@PathVariable String type, 
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("theaters", theaterService.getAllTheaters());
		    model.addAttribute("movies", movieService.getAllMovies());
    		model.addAttribute("editMode", false);
            return "addShowtime";
        }
		
		newshowtime.setMovie(movieService.getMovieById(newshowtime.getMovie().getMovieId()));
	    newshowtime.setTheater(theaterService.getTheaterById(newshowtime.getTheater().getTheaterId()));

	    showtimeService.saveShowtime(newshowtime);

	    Movie movie = newshowtime.getMovie();
	    movie.getShowtimes().add(newshowtime);
	    movieService.updateMovie(movie);

	    Theater theater = newshowtime.getTheater();
	    theater.getShowtimes().add(newshowtime);
	    theaterService.updateTheater(theater);

	    return type.equals("movie") ? "redirect:/movie/details?movieId=" + movie.getMovieId() : 
	    	"redirect:/theater/details?theaterId=" + theater.getTheaterId();
	}

	@PostMapping("/edit/{type}")
	public String showEditShowtimeForm(@RequestParam("showtimeId") Long id, @PathVariable String type, Model model) {
	    Showtime showtime = showtimeService.getShowtimeById(id);

	    if (showtime != null) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        String formattedStartDate = showtime.getStartTime().format(formatter);
	        model.addAttribute("formattedStartDate", formattedStartDate);
	        String formattedEndDate = showtime.getEndTime().format(formatter);
	        model.addAttribute("formattedEndDate", formattedEndDate);

	        model.addAttribute("newshowtime", showtime);
	        model.addAttribute("movies", movieService.getAllMovies());
	        model.addAttribute("theaters", theaterService.getAllTheaters());
	        model.addAttribute("editMode", true);
	        model.addAttribute("showTimeFor", type);

	        return "addShowtime";
	    } else {
	        return "redirect:/showtime";
	    }
	}

	@PostMapping("/editSave/{type}")
	public String editShowtime(@Valid @ModelAttribute("newshowtime") Showtime editedShowtime, 
			BindingResult bindingResult, 
			@PathVariable String type, 
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("theaters", theaterService.getAllTheaters());
		    model.addAttribute("movies", movieService.getAllMovies());
    		model.addAttribute("editMode", true);
            return "addShowtime";
        }
		
		Long movieId = editedShowtime.getMovie().getMovieId();
		Long theaterId = editedShowtime.getTheater().getTheaterId();
	    editedShowtime.setMovie(movieService.getMovieById(movieId));
	    editedShowtime.setTheater(theaterService.getTheaterById(theaterId));
	    showtimeService.updateShowtime(editedShowtime);

	    return type.equals("movie") ? "redirect:/movie/details?movieId=" + movieId : 
	    	"redirect:/theater/details?theaterId=" + theaterId;
	}

	@PostMapping("/delete/{type}")
	public String deleteShowtime(@RequestParam("showtimeId") Long id, @PathVariable String type) {
		Long movieId = showtimeService.getShowtimeById(id).getMovie().getMovieId();
		Long theaterId = showtimeService.getShowtimeById(id).getTheater().getTheaterId();
	    Showtime showtime = showtimeService.getShowtimeById(id);
	    showtimeService.deleteShowtime(showtime);
	    
	    return type.equals("movie") ? "redirect:/movie/details?movieId=" + movieId : 
	    	"redirect:/theater/details?theaterId=" + theaterId;
	}


}

