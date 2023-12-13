package com.csye6220.finalprojectesd.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csye6220.finalprojectesd.dao.UserDAO;
import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;

@Service
public class UserService implements UserDetailsService {
	
	private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}
	
    public User getUserByUsername(String username) {
    	return userDAO.getUserByUsername(username);
    }
    
    public User getUserByEmail(String email) {
    	return userDAO.getUserByEmail(email);
    }
    
    public User getUserById(Long id) {
    	return userDAO.getUserById(id);
    }
    
    public List<User> getUserByRole(UserRole role) {
    	return userDAO.getUserByRole(role);
    }
    
    public List<User> getAllUsers() {
    	return userDAO.getAllUsers();
    }
    
    public void updateUser(User user) {
    	userDAO.updateUser(user);
    }
    
    public void deleteUser(User user) {
    	userDAO.deleteUser(user);
    }
    
    public List<Review> findReviewsByUser(User user) {
    	return userDAO.findReviewsByUser(user);
    }
    
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
    	return userDAO.getUserByUsernameOrEmail(usernameOrEmail);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsernameOrEmail(username);        if (user == null) {
        	throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }
}

