package com.csye6220.finalprojectesd.model;

import java.util.Date;

import jakarta.persistence.*;

public class Notification {

	private User user;
	
	private String message;
	
	private Date timestamp;
	
	public Notification() {
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
