package com.csye6220.finalprojectesd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;
import com.csye6220.finalprojectesd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController{
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
	
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
    public String login() {;
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
    		userService.saveUser(newUser);
    		redirectAttributes.addFlashAttribute("success", "Email registeration is complete. Login to continue.");
    		return "redirect:/login";
    	}
    }
    
    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
    	System.out.println(principal);
    	String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user, Model model) {
    	userService.updateUser(user);
        model.addAttribute("message", "Profile updated successfully!");
        return "profile";
    }

    @GetMapping("/password")
    public String showPasswordChangeForm(Model model) {
        return "passwordChangeForm";
    }

    @PostMapping("/password/change")
    public String changePassword(Model model) {
        return "passwordChangeForm";
    }
    
    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
    	request.getSession().invalidate();
        return "redirect:/";
    }
    
}
