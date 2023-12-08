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
	public String getShowtimesPage(Model model) {
		List<Showtime> searchResults = showtimeService.getAllShowtimes();
		model.addAttribute("showtimes", searchResults);
	    return "showtimes";
	}
	
	@GetMapping("/add/{theaterId}")
    public String showAddShowtimesForm(@PathVariable Long theaterId, Model model) {
//		Showtime showtime = new Showtime();
//        Theater theater = new Theater();
//        theater.setTheaterId(theaterId);
//        showtime.setTheater(theater);
        model.addAttribute("newshowtime", new Showtime());
        model.addAttribute("theaters", theaterService.getAllTheaters());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("editMode", false);
        return "addShowtime";
    }
    
    @PostMapping("/add")
    public String addShowtime(@ModelAttribute Showtime newshowtime, Model model) {
    	model.addAttribute("movie", movieService.getMovieById(newshowtime.getMovie().getMovieId()));
    	showtimeService.saveShowtime(newshowtime);
        return "redirect:/showtime";
    }

    @PostMapping("/edit")
    public String showEditShowtimeForm(@RequestParam("showtimeId") Long id, Model model) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        if (showtime != null) {
            model.addAttribute("editedShowtime", showtime);
            model.addAttribute("movies", movieService.getAllMovies());
            model.addAttribute("theaters", theaterService.getAllTheaters());
            model.addAttribute("editMode", true);
            return "addShowtime";
        } else {
            return "redirect:/showtime";
        }
    }

    @PostMapping("/editSave")
    public String editShowtime(@ModelAttribute Showtime editedShowtime, Model model) {
    	showtimeService.updateShowtime(editedShowtime);
        return "redirect:/showtime";
    }

    @PostMapping("/delete")
    public String deleteShowtime(@RequestParam("showtimeId") Long id) {
    	Showtime showtime = showtimeService.getShowtimeById(id);
    	showtimeService.deleteShowtime(showtime);
        return "redirect:/showtime";
    }

}

