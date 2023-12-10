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
import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.Theater;
import com.csye6220.finalprojectesd.service.ShowtimeService;
import com.csye6220.finalprojectesd.service.TheaterService;

@Controller
@RequestMapping("/theater")
public class TheaterController {
    
	@Autowired
    private TheaterService theaterService;
    
	@Autowired
	private ShowtimeService showtimeService;
	
	@GetMapping
	public String getTheatersPage(Model model) {
		List<Theater> searchResults = theaterService.getAllTheaters();
		model.addAttribute("theaters", searchResults);
		System.out.println(searchResults.size());
	    return "theaters";
	}
	
    @GetMapping("/add")
    public String showAddTheatresForm(Model model) {
        model.addAttribute("newtheater", new Theater());
        model.addAttribute("editMode", false);
        return "addTheater";
    }
    
    @PostMapping("/add")
    public String addTheater(@ModelAttribute Theater newTheater, Model model) {
    	theaterService.saveTheater(newTheater);
        return "redirect:/theater";
    }
    
    @PostMapping("/details")
    public String viewTheaterDetails(@RequestParam("theaterId") Long id, Model model) {
        Theater theater = theaterService.getTheaterById(id);
        List<Showtime> showtimes = showtimeService.getAllShowtimesByTheater(theater);
        model.addAttribute("theater", theater);
        model.addAttribute("showtimes", showtimes);
        return "theaterDetails";
    }

    @PostMapping("/edit")
    public String showEditTheaterForm(@RequestParam("theaterId") Long id, Model model) {
        Theater theater = theaterService.getTheaterById(id);

        if (theater != null) {
            model.addAttribute("newtheater", theater);
            model.addAttribute("editMode", true);
            return "addTheater";
        } else {
            return "redirect:/theater";
        }
    }

    @PostMapping("/editSave")
    public String editTheater(@ModelAttribute Theater editedTheater, Model model) {
    	theaterService.updateTheater(editedTheater);
        return "redirect:/theater";
    }

    @PostMapping("/delete")
    public String deleteTheater(@RequestParam("theaterId") Long id) {
    	theaterService.deleteTheater(id);
        return "redirect:/theater";
    }

}
