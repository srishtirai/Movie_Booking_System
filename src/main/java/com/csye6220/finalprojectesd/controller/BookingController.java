package com.csye6220.finalprojectesd.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csye6220.finalprojectesd.model.Booking;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.service.BookingService;
import com.csye6220.finalprojectesd.service.ShowtimeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
	@Autowired
	private ShowtimeService showtimeService;
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping
    public String bookingService(Model model) {
        return "homePage";
    }
	
	@PostMapping("/add")
    public String viewMovieDetails(
    		@RequestParam("showtimeId") Long showtimeId, 
    		@RequestParam("numberOfTickets") int numberOfTickets, 
    		@ModelAttribute("currentUser") User user, 
    		Model model, 
    		HttpServletRequest request,
    		RedirectAttributes redirectAttributes
    ) {
		Long bookedSeats = bookingService.getBookingCountByShowtimeId(showtimeId);
		int totalSeats = showtimeService.getShowtimeById(showtimeId).getTotalSeats();
		
		int remainingSeats = (int) (totalSeats - bookedSeats);
		
		if(remainingSeats == 0) {
			redirectAttributes.addFlashAttribute("error", "No seats available. Sorry we are filled !!");
		} else if(numberOfTickets > remainingSeats) {
			redirectAttributes.addFlashAttribute("error", "Booking failed !! Only " + remainingSeats + " tickets are available");
		} else {
			Booking booking = new Booking();
			booking.setUser(user);
			booking.setShowTime(showtimeService.getShowtimeById(showtimeId));
			booking.setNumberOfTickets(numberOfTickets);
			booking.setBookingDateTime(LocalDateTime.now());
			bookingService.saveBooking(booking);
			
			redirectAttributes.addFlashAttribute("success", "Booking successfull !!");
		}
		Long movieId = showtimeService.getShowtimeById(showtimeId).getMovie().getMovieId();

		return "redirect:/movie/details?movieId=" + movieId;

    }
	
	
}
