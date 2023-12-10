package com.csye6220.finalprojectesd.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.finalprojectesd.model.Booking;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.service.BookingService;
import com.csye6220.finalprojectesd.service.ShowtimeService;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
	@Autowired
	private ShowtimeService showtimeService;
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping
    public String showHomePage(Model model) {
        return "homePage";
    }
	
	@GetMapping("/add")
    public String viewMovieDetails(@RequestParam("showtimeId") Long id, @RequestParam("numberOfTickets") int numberOfTickets, @ModelAttribute("currentUser") User user, Model model) {
		Booking booking = new Booking(user, showtimeService.getShowtimeById(id), numberOfTickets, LocalDateTime.now());
		bookingService.saveBooking(booking);
        return "redirect:/bookingSuccess";
    }
}
