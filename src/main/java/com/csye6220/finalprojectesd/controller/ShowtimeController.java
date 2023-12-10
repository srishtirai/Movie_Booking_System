package com.csye6220.finalprojectesd.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@GetMapping("/add/theater/{theaterId}")
    public String showAddShowtimesForTheaterForm(@PathVariable Long theaterId, Model model) {
		Showtime showtime = new Showtime();
		showtime.setTheater(theaterService.getTheaterById(theaterId));
		
        model.addAttribute("newshowtime", showtime);
        model.addAttribute("theaters", theaterService.getAllTheaters());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("editMode", false);
        model.addAttribute("showTimeFor", "theater");
        
        return "addShowtime";
    }
	
	@GetMapping("/add/movie/{movieId}")
    public String showAddShowtimesForMovieForm(@PathVariable Long movieId, Model model) {
		Showtime showtime = new Showtime();
		showtime.setMovie(movieService.getMovieById(movieId));
		
        model.addAttribute("newshowtime", showtime);
        model.addAttribute("theaters", theaterService.getAllTheaters());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("editMode", false);
        model.addAttribute("showTimeFor", "movie");
        
        return "addShowtime";
    }
    
    @PostMapping("/add/movie")
    public String addShowtimeToMovie(@ModelAttribute Showtime newshowtime, Model model) {
    	newshowtime.setMovie(movieService.getMovieById(newshowtime.getMovie().getMovieId()));
    	newshowtime.setTheater(theaterService.getTheaterById(newshowtime.getTheater().getTheaterId()));
    	
    	showtimeService.saveShowtime(newshowtime);
    	
    	Movie movie = newshowtime.getMovie();
    	movie.getShowtimes().add(newshowtime);
    	movieService.updateMovie(movie);
    	
    	Theater theater = newshowtime.getTheater();
    	theater.getShowtimes().add(newshowtime);
    	theaterService.updateTheater(theater);
    	
    	return "redirect:/movie";
    }
    
    @PostMapping("/add/theater")
    public String addShowtimeToTheater(@ModelAttribute Showtime newshowtime, Model model) {
    	newshowtime.setMovie(movieService.getMovieById(newshowtime.getMovie().getMovieId()));
    	newshowtime.setTheater(theaterService.getTheaterById(newshowtime.getTheater().getTheaterId()));
    	
    	showtimeService.saveShowtime(newshowtime);
    	
    	Movie movie = newshowtime.getMovie();
    	movie.getShowtimes().add(newshowtime);
    	movieService.updateMovie(movie);
    	
    	Theater theater = newshowtime.getTheater();
    	theater.getShowtimes().add(newshowtime);
    	theaterService.updateTheater(theater);
    	
    	return "redirect:/theater";
    }

    @PostMapping("/edit/movie")
    public String showEditShowtimeForMovieForm(@RequestParam("showtimeId") Long id, Model model) {
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
            model.addAttribute("showTimeFor", "movie");
            
            return "addShowtime";
        } else {
            return "redirect:/showtime";
        }
    }
    
    @PostMapping("/edit/theater")
    public String showEditShowtimeForTheaterForm(@RequestParam("showtimeId") Long id, Model model) {
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
            model.addAttribute("showTimeFor", "theater");
            
            return "addShowtime";
        } else {
            return "redirect:/showtime";
        }
    }

    @PostMapping("/editSave/movie")
    public String editShowtimeForMovie(@ModelAttribute("newshowtime") Showtime editedShowtime, Model model) {
    	editedShowtime.setMovie(movieService.getMovieById(editedShowtime.getMovie().getMovieId()));
    	editedShowtime.setTheater(theaterService.getTheaterById(editedShowtime.getTheater().getTheaterId()));
    	showtimeService.updateShowtime(editedShowtime);
        return "redirect:/movie";
    }
    
    @PostMapping("/editSave/theater")
    public String editShowtimeForTheater(@ModelAttribute("newshowtime") Showtime editedShowtime, Model model) {
    	editedShowtime.setMovie(movieService.getMovieById(editedShowtime.getMovie().getMovieId()));
    	editedShowtime.setTheater(theaterService.getTheaterById(editedShowtime.getTheater().getTheaterId()));
    	showtimeService.updateShowtime(editedShowtime);
        return "redirect:/theater";
    }

    @PostMapping("/delete/movie")
    public String deleteMovieShowtime(@RequestParam("showtimeId") Long id) {
    	Showtime showtime = showtimeService.getShowtimeById(id);
    	showtimeService.deleteShowtime(showtime);
        return "redirect:/movie";
    }
    
    @PostMapping("/delete/theater")
    public String deleteTheaterShowtime(@RequestParam("showtimeId") Long id) {
    	Showtime showtime = showtimeService.getShowtimeById(id);
    	showtimeService.deleteShowtime(showtime);
        return "redirect:/theater";
    }

}

