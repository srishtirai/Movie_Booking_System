package com.csye6220.finalprojectesd.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csye6220.finalprojectesd.model.Booking;
import com.csye6220.finalprojectesd.model.Email;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.User;

@Service
public class EmailService {

	@Autowired 
	private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}")
    private String sender;
 
    public boolean sendSimpleMail(Email details) {
    	
        try {
            SimpleMailMessage mailData = new SimpleMailMessage();
 
            mailData.setFrom(sender);
            mailData.setTo(details.getRecipient());
            mailData.setText(details.getBody());
            mailData.setSubject(details.getSubject());
 
            javaMailSender.send(mailData);
            
            return true;
        }
 
        catch (Exception e) {
            return false;
        }
    }
    
    public void sendBookingConfirmationEmail(User user, Showtime showtime, Booking booking) {
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
        sendSimpleMail(emailDetails);
	}
    
    public void sendStaffAccountCreationConfirmationEmail(User user) {
    	String emailBody = "Dear " + user.getUsername() + ",\n\n"
		        + "Thank you for registering with us.\n\n"
		        + "You will be able to Login once your account has been verified by our Admin.\n\n"
		        + "We look forward to working with you!\n\n"
		        + "Best regards,\nYour Movie Booking Team";
		
		Email emailDetails = new Email(user.getEmail(), "Email Registeration Successful", emailBody);
		sendSimpleMail(emailDetails);
    }
    
    public void sendStaffAccountApprovalEmail(User user) {
    	String emailBody = "Dear " + user.getUsername() + ",\n\n"
		        + "Thank you for registering with us.\n\n"
		        + "Your account has been verified by our Admin. You will now be able to login.\n\n"
		        + "We look forward to working with you!\n\n"
		        + "Best regards,\nYour Movie Booking Team";
		
		Email emailDetails = new Email(user.getEmail(), "Email Registeration Approved", emailBody);
        sendSimpleMail(emailDetails);
    }
}
