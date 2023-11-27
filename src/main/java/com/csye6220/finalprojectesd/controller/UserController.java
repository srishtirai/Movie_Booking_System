package com.csye6220.finalprojectesd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.finalprojectesd.dao.UserDAO;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.service.UserService;

@Controller
public class UserController{
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
    
    @GetMapping("/signUp")
    public String showSignUpForm() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String userSignUp(@ModelAttribute User user, Model model) {
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
    
    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
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
    public String logoutUser() {
        return "redirect:/login";
    }
    
}
