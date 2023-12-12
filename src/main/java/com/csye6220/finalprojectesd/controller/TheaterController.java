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

import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.Theater;
import com.csye6220.finalprojectesd.service.ShowtimeService;
import com.csye6220.finalprojectesd.service.TheaterService;

import jakarta.servlet.http.HttpServletRequest;

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
    
    @GetMapping("/details")
    public String viewTheaterDetails(@RequestParam("theaterId") Long id, Model model, HttpServletRequest request) {
        Theater theater = theaterService.getTheaterById(id);
        List<Showtime> showtimes = showtimeService.getAllShowtimesByTheater(theater);
        model.addAttribute("theater", theater);
        model.addAttribute("showtimes", showtimes);
        
        Map<String, ?> flashAttributes = RequestContextUtils.getInputFlashMap(request);
        if (flashAttributes != null) {
            model.mergeAttributes(flashAttributes);
        }
        
        return "theaterDetails";
    }

    @PostMapping("/edit")
    public String showEditTheaterForm(@RequestParam("theaterId") Long id, Model model) {
        Theater theater = theaterService.getTheaterById(id);

        if (theater != null) {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
            String formattedOpeningTime = theater.getOpeningTime().format(formatter);
            model.addAttribute("formattedOpeningTime", formattedOpeningTime);
            String formattedClosingTime = theater.getClosingTime().format(formatter);
            model.addAttribute("formattedClosingTime", formattedClosingTime);
        	
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
