package com.csye6220.finalprojectesd.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.service.BookingService;
import com.csye6220.finalprojectesd.service.EmailService;
import com.csye6220.finalprojectesd.service.ShowtimeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
	@Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.api.public-key}")
    private String stripePublicKey;

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
	
	@PostMapping("/confirm")
    public String confirmBooking (
    		@RequestParam("showtimeId") Long showtimeId, 
    		@RequestParam("numberOfTickets") int numberOfTickets, 
    		@ModelAttribute("currentUser") User user, 
    		Model model, 
    		HttpServletRequest request,
    		RedirectAttributes redirectAttributes
    ) {
		Long bookedSeats = Optional.ofNullable(bookingService.getBookingCountByShowtimeId(showtimeId)).orElse(0L);
		int totalSeats = showtimeService.getShowtimeById(showtimeId).getTotalSeats();
		
		int remainingSeats = (int) (totalSeats - bookedSeats);
		
		if(remainingSeats == 0) {
			redirectAttributes.addFlashAttribute("error", "No seats available. Sorry we are filled !!");
		} else if(numberOfTickets > remainingSeats) {
			redirectAttributes.addFlashAttribute("error", "Booking failed !! Only " + remainingSeats + " tickets are available.");
		} else {
			int totalAmount = numberOfTickets * 10;
	        
	        model.addAttribute("numberOfTickets", numberOfTickets);
	        model.addAttribute("showtimeId", showtimeId);
	        model.addAttribute("amount", totalAmount);
	        model.addAttribute("stripePublicKey", stripePublicKey);
	        
	        return "paymentCheckout";
		}
		Long movieId = showtimeService.getShowtimeById(showtimeId).getMovie().getMovieId();

		return "redirect:/movie/details?movieId=" + movieId;

    }
	
	 @PostMapping("/charge")
	    public String charge(
	    		@RequestParam("showtimeId") Long showtimeId,
	            @RequestParam("numberOfTickets") int numberOfTickets,
	            @RequestParam("stripeToken") String token,
	            @RequestParam("amount") String amount,
	            @ModelAttribute("currentUser") User user, 
	            RedirectAttributes redirectAttributes,
	    		Model model) {
	        try {
	            Stripe.apiKey = stripeApiKey;

	            Map<String, Object> params = new HashMap<>();
	            params.put("amount", Integer.parseInt(amount)*100);
	            params.put("currency", "USD");
	            params.put("description", "Movie Booking");
	            params.put("source", token);

	            Charge charge = Charge.create(params);
				Showtime showtime = showtimeService.getShowtimeById(showtimeId);
				
				Booking booking = new Booking();
				booking.setUser(user);
				booking.setShowTime(showtime);
				booking.setNumberOfTickets(numberOfTickets);
				booking.setBookingDateTime(LocalDateTime.now());
				bookingService.saveBooking(booking);
				
				emailService.sendBookingConfirmationEmail(user, showtime, booking);

	            redirectAttributes.addFlashAttribute("chargeId", charge.getId());
	            redirectAttributes.addFlashAttribute("status", charge.getStatus());
	            redirectAttributes.addFlashAttribute("amount", charge.getAmount()/100);
	            redirectAttributes.addFlashAttribute("paymentSuccess", true);
				redirectAttributes.addFlashAttribute("success", "Booking successfull !!");

	        } catch (StripeException e) {
	            redirectAttributes.addFlashAttribute("error", "Payment Error");
	        	redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	        	redirectAttributes.addFlashAttribute("paymentSuccess", false);
	        }
	        
	        Long movieId = showtimeService.getShowtimeById(showtimeId).getMovie().getMovieId();

			return "redirect:/movie/details?movieId=" + movieId;
	    }
	
}
