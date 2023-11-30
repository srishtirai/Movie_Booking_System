package com.csye6220.finalprojectesd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue
    @Column(name="user_id")
    private Long userId;
	
    private String username;
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    private String email;
    
    @Column(name="phone_number")
    private Long phoneNumber;

    public User() {
    }

    public User(String username, String password, UserRole role, String email, Long phoneNumber) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }  
    
}
