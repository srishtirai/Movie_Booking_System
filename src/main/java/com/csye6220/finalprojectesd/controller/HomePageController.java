package com.csye6220.finalprojectesd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.csye6220.finalprojectesd.model.Movie;
//import com.csye6220.finalprojectesd.model.User;
//import com.csye6220.finalprojectesd.model.UserRole;
//import com.csye6220.finalprojectesd.service.MovieService;
//import com.csye6220.finalprojectesd.service.UserService;

@Controller
@RequestMapping("/")
public class HomePageController {
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
//    @Autowired
//    private UserService userService;
    
    @GetMapping
    public String showHomePage(Model model) {
//    	User newUser = new User("movieadmin", "movieadmin@gmail.com", passwordEncoder.encode("Srishti@99"), UserRole.ADMIN, 4134567890L);
//    	newUser.setEnabled(true);
//		userService.saveUser(newUser);
        return "homePage";
    }
}
