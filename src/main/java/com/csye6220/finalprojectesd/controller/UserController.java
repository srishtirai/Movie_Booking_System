package com.csye6220.finalprojectesd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.finalprojectesd.dao.UserDAO;
import com.csye6220.finalprojectesd.model.User;

@Controller
public class UserController{
    
    @Autowired
    private UserDAO userDAO;
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userDAO.getUserByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "redirect:/dashboard";
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
    public String showUserProfile() {
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(Model model) {
        model.addAttribute("message", "Profile updated successfully!");
        return "profile";
    }

    @GetMapping("/password")
    public String showPasswordChangeForm(Model model) {
        return "passwordChangeForm";
    }

    @PostMapping("/password/change")
    public String changePassword(Model model) {
        // TODO: Logic to handle password change
        return "passwordChangeForm";
    }
}
