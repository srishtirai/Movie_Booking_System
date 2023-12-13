package com.csye6220.finalprojectesd.model;

import jakarta.validation.constraints.Size;

public class PasswordUpdate {

    private String currentPassword;
    
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;
    
    private String confirmPassword;
    
	public PasswordUpdate() {
	}

	public String getCurrentPassword() {
		return currentPassword;
	}
	
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
