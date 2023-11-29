package com.csye6220.finalprojectesd.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
	private Movie movie;
	
	private int rating;
	
	private String comment;
	
	private LocalDateTime reviewDate;
	
	public Review() {
		
	}
	
	public Review(Long reviewId, User user, Movie movie, int rating, String comment, LocalDateTime reviewDate) {
		super();
		this.reviewId = reviewId;
		this.user = user;
		this.movie = movie;
		this.rating = rating;
		this.comment = comment;
		this.reviewDate = reviewDate;
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
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public LocalDateTime getReviewDate() {
		return reviewDate;
	}
	
	public void setReviewDate(LocalDateTime reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
}
