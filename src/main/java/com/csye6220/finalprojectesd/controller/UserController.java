package com.csye6220.finalprojectesd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csye6220.finalprojectesd.model.Email;
import com.csye6220.finalprojectesd.model.PasswordUpdate;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;
import com.csye6220.finalprojectesd.service.EmailService;
import com.csye6220.finalprojectesd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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
        return "login";
    }
    
    @PostMapping("/login")
    public String login() {
    	return "redirect:/";
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
    	model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String userSignUp(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
    	if (bindingResult.hasErrors()) {
            return "signup";
        }
    	
    	if (userService.getUserByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
            return "signup";
        }
    	
    	if (userService.getUserByEmail(user.getEmail()) != null) {
    		redirectAttributes.addFlashAttribute("error", "Email is already registered. Login to continue.");
            return "redirect:/login";
        }	else {
    		user.setEnabled(user.getRole().equals(UserRole.USER));
    		user.setPassword(passwordEncoder.encode(user.getPassword()));

    		userService.saveUser(user);
    		emailService.sendStaffAccountCreationConfirmationEmail(user);
    		
    		redirectAttributes.addFlashAttribute("success", "Email registeration is complete. Login to continue.");
    		return "redirect:/login";
    	}
    }
    
    @GetMapping("/profile")
    public String showUserProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    	model.addAttribute("passwordUpdate", new PasswordUpdate());
    	model.addAttribute("user", userDetails);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
    		@ModelAttribute("currentUser") User currentUser, 
    		@Valid @ModelAttribute("user") User user, 
    		BindingResult bindingResult, 
    		Model model) {
    	if (bindingResult.hasErrors()) {
    		bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
    		FieldError firstFieldError = bindingResult.getFieldErrors().stream().findFirst().orElse(null);

    		if(bindingResult.getAllErrors().size() == 1 && 
    				firstFieldError != null && 
    				firstFieldError.getDefaultMessage().equals("Password is required")) {
    			user.setPassword(currentUser.getPassword());
    		} else {
    			return "profile";
    		}
        }
    	user.setEnabled(true);
    	User newUser = userService.getUserByUsername(user.getUsername());
    	
    	if (newUser != null && newUser.getUserId() != currentUser.getUserId()) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
            return "profile";
        }
    	model.addAttribute("success", "User details updated successfully!");
    	userService.updateUser(user);
        return "profile";
    }
    
    @PostMapping("/profile/update-password")
    public String updatePassword(
    		@AuthenticationPrincipal UserDetails userDetails,
    		@ModelAttribute("currentUser") User currentUser, 
    		@Valid @ModelAttribute("passwordUpdate") PasswordUpdate passwordUpdate,
            BindingResult bindingResult, Model model) {
    	model.addAttribute("user", userDetails);
    	
        if (bindingResult.hasErrors()) {
        	bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "profile";
        }

        if (!passwordEncoder.matches(passwordUpdate.getCurrentPassword(), currentUser.getPassword())) {
            bindingResult.rejectValue("currentPassword", "error.passwordUpdate", "Incorrect current password");
            return "profile";
        }

        if (passwordEncoder.matches(passwordUpdate.getNewPassword(), currentUser.getPassword())) {
            bindingResult.rejectValue("newPassword", "error.passwordUpdate", "New password cannot be same as the current password");
            return "profile";
        }
        
        if (!passwordUpdate.getConfirmPassword().matches(passwordUpdate.getNewPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.passwordUpdate", "Confirm password should match the new password");
            return "profile";
        }
       
        currentUser.setPassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));
        userService.updateUser(currentUser);

        model.addAttribute("success", "Password updated successfully!");
        return "redirect:/profile";
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
        emailService.sendStaffAccountApprovalEmail(user);
        
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
