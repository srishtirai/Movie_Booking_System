package com.csye6220.finalprojectesd.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.csye6220.finalprojectesd.model.Email;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.service.BookingService;
import com.csye6220.finalprojectesd.service.EmailService;
import com.csye6220.finalprojectesd.service.ShowtimeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
	@Autowired
	private ShowtimeService showtimeService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired 
	private EmailService emailService;
	
	@GetMapping
    public String bookingService(Model model) {
        return "homePage";
    }
	
	@PostMapping("/add")
    public String confirmBooking (
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
			Showtime showtime = showtimeService.getShowtimeById(showtimeId);
			
			Booking booking = new Booking();
			booking.setUser(user);
			booking.setShowTime(showtime);
			booking.setNumberOfTickets(numberOfTickets);
			booking.setBookingDateTime(LocalDateTime.now());
			bookingService.saveBooking(booking);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			
			String emailBody = "Dear " + user.getUsername() + ",\n\n"
			        + "Thank you for booking tickets with us. Here are your booking details:\n\n"
			        + "Movie: " + showtime.getMovie().getTitle() + "\n"
			        + "Show Time: " + showtime.getStartTime().format(formatter) + "\n"
			        + "Number of Tickets: " + booking.getNumberOfTickets() + "\n"
			        + "Booking Date and Time: " + booking.getBookingDateTime().format(formatter) + "\n\n"
			        + "We look forward to seeing you at the movie!\n\n"
			        + "Best regards,\nYour Movie Booking Team";
			
			Email emailDetails = new Email(user.getEmail(), "Movie Booking Successful", emailBody);
	        emailService.sendSimpleMail(emailDetails);
			
			redirectAttributes.addFlashAttribute("success", "Booking successfull !!");
		}
		Long movieId = showtimeService.getShowtimeById(showtimeId).getMovie().getMovieId();

		return "redirect:/movie/details?movieId=" + movieId;

    }
	
	
}
