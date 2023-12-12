package com.csye6220.finalprojectesd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csye6220.finalprojectesd.model.Email;

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
}
