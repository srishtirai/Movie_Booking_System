package com.csye6220.finalprojectesd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csye6220.finalprojectesd.model.Email;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;
import com.csye6220.finalprojectesd.service.EmailService;
import com.csye6220.finalprojectesd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController{
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
	private EmailService emailService;
	
    @ModelAttribute("allRoles")
    public UserRole[] getAllRoles() {
        return UserRole.values();
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
    	model.addAttribute("type", "login");
        return "login";
    }
    
    @PostMapping("/login")
    public String login() {
    	return "redirect:/";
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
    	model.addAttribute("user", new User());
    	model.addAttribute("type", "signup");
        return "login";
    }

    @PostMapping("/signup")
    public String userSignUp(@RequestParam String role, @RequestParam String userid, @RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
    	User user = userService.getUserByEmail(username);
    	if(user != null) {
    		redirectAttributes.addFlashAttribute("error", "Email is already registered. Login to continue.");
            return "redirect:/login";
    	} else {
    		UserRole roleValue = UserRole.valueOf(role);
    		User newUser = new User(userid, username, passwordEncoder.encode(password), roleValue, 123456789L);
    		newUser.setEnabled(role.equals("USER"));
    		userService.saveUser(newUser);
    		
    		String emailBody = "Dear " + userid + ",\n\n"
			        + "Thank you for registering with us.\n\n"
			        + "You will be able to Login once your account has been verified by our Admin.\n\n"
			        + "We look forward to working with you!\n\n"
			        + "Best regards,\nYour Movie Booking Team";
    		
    		Email emailDetails = new Email(username, "Email Registeration Successful", emailBody);
	        emailService.sendSimpleMail(emailDetails);
    		
    		redirectAttributes.addFlashAttribute("success", "Email registeration is complete. Login to continue.");
    		return "redirect:/login";
    	}
    }
    
    @GetMapping("/profile")
    public String showUserProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    	model.addAttribute("user", userDetails);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user, Model model) {
    	userService.updateUser(user);
        return "profile";
    }
    
    @GetMapping("/users")
    public String showStaffList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    	if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
    		model.addAttribute("userList", userService.getAllUsers().stream().filter(user -> user.getRole() == UserRole.STAFF || user.getRole() == UserRole.USER).toList());
    	} else {
    		model.addAttribute("userList", userService.getAllUsers().stream().filter(user -> user.getRole() == UserRole.USER).toList());
    	}
        return "users";
    }
    
    @GetMapping("/users/approve/{userId}")
    public String approveStaff(@PathVariable Long userId, Model model) {
    	User user = userService.getUserById(userId);
    	user.setEnabled(true);
    	userService.updateUser(user);
    	
    	String emailBody = "Dear " + user.getUsername() + ",\n\n"
		        + "Thank you for registering with us.\n\n"
		        + "Your account has been verified by our Admin. You will now able to login.\n\n"
		        + "We look forward to working with you!\n\n"
		        + "Best regards,\nYour Movie Booking Team";
		
		Email emailDetails = new Email(user.getEmail(), "Email Registeration Approved", emailBody);
        emailService.sendSimpleMail(emailDetails);
        
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUsers(@PathVariable Long userId, Model model) {
    	User user = userService.getUserById(userId);
    	userService.deleteUser(user);
        return "redirect:/users";
    }
    
    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
    	request.getSession().invalidate();
        return "redirect:/";
    }
    
}
