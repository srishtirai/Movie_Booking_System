package com.csye6220.finalprojectesd.model;

import java.util.Date;

public class Review {
	private Long reviewId;
	private User user;
	private Movie movie;
	private int rating;
	private String commnet;
	private Date reviewDate;
	
	public Review() {
		
	}
	
	public Long getReviewId() {
		return reviewId;
	}
	
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getCommnet() {
		return commnet;
	}
	
	public void setCommnet(String commnet) {
		this.commnet = commnet;
	}
	
	public Date getReviewDate() {
		return reviewDate;
	}
	
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
}
