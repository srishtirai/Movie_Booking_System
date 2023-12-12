package com.csye6220.finalprojectesd.dao;

import java.util.List;

import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;

public interface UserDAO {
    public void saveUser(User user);
    public User getUserByEmail(String email);
    public User getUserByUsername(String username);
    public User getUserById(Long id);
    public List<User> getUserByRole(UserRole role);
    public List<User> getAllUsers();
    public void updateUser(User user);
    public void deleteUser(User user);
    public List<Review> findReviewsByUser(User user);
}
