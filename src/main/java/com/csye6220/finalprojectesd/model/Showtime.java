package com.csye6220.finalprojectesd.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "showtimes")
public class Showtime {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="showtime_id")
    private Long showtimeId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@NotNull(message = "Movie is required")
    @JoinColumn(name = "movie_id")
    private Movie movie;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@NotNull(message = "Theater is required")
    @JoinColumn(name = "theater_id")
    private Theater theater;
    
	@NotNull(message = "Start time is required")
	@Column(name="start_time")
    private LocalDateTime startTime;
    
	@NotNull(message = "End time is required")
	@Column(name="end_time")
    private LocalDateTime endTime;
    
	@Min(value = 1, message = "Total seats must be greater than 0")
	@Column(name="total_seats")
    private int totalSeats;

	@AssertTrue(message = "End time should be after start time")
	public boolean getIsOpeningTimeBeforeClosingTime() {
        return (endTime != null && startTime != null) && endTime.isAfter(startTime);
	}
	 
    public Showtime() {
    }

	public Showtime(Long showtimeId, Movie movie, Theater theater, LocalDateTime startTime, LocalDateTime endTime,
			int totalSeats) {
		this.showtimeId = showtimeId;
		this.movie = movie;
		this.theater = theater;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalSeats = totalSeats;
	}

	public Long getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(Long showtimeId) {
		this.showtimeId = showtimeId;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
}
