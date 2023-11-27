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

import com.csye6220.finalprojectesd.model.Theater;
import com.csye6220.finalprojectesd.service.TheaterService;

@Controller
@RequestMapping("/theater")
public class TheaterController {
    
	@Autowired
    private TheaterService theaterService;
    
    @GetMapping("/add")
    public String showAddTheatresForm(Model model) {
    	Theater newTheater = new Theater();
        model.addAttribute("newtheater", newTheater);
        return "addTheater";
    }
    
    @PostMapping("/add")
    public String addTheater(@ModelAttribute Theater newTheater, Model model) {
    	theaterService.saveTheater(newTheater);
        return "redirect:/theater/list";
    }
    
    @GetMapping("/list")
    public String listTheaters(Model model) {
    	 List<Theater> theater = theaterService.getAllTheaters();
         model.addAttribute("theaters", theater);
        return "theaterList";
    }
    
    @GetMapping("/{id}")
    public String viewTheaterServiceDetails(@PathVariable Long id, Model model) {
        Theater theater = theaterService.getTheaterById(id);
        model.addAttribute("theater", theater);
        return "redirect:/theater/list";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditTheaterForm(@PathVariable Long id, Model model) {
        Theater theater = theaterService.getTheaterById(id);

        if (theater != null) {
            model.addAttribute("editedTheater", theater);
            return "editTheater";
        } else {
            return "redirect:/theater/list";
        }
    }

    @PostMapping("/edit")
    public String editTheater(@ModelAttribute Theater editedTheater, Model model) {
    	theaterService.updateTheater(editedTheater);
        return "redirect:/theater/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
    	theaterService.deleteTheater(id);
        return "redirect:/theater/list";
    }

}
