package com.csye6220.finalprojectesd.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
    private Long bookingId;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "showtime_id")
    private Showtime showTime;
    
	@Column(name="no_of_tickets")
    private int numberOfTickets;
    
	@Column(name="booking_date")
    private LocalDateTime bookingDateTime;

    public Booking() {
    }
    
	public Booking(Long bookingId, User user, Showtime showTime, int numberOfTickets, LocalDateTime bookingDateTime) {
		this.bookingId = bookingId;
		this.user = user;
		this.showTime = showTime;
		this.numberOfTickets = numberOfTickets;
		this.bookingDateTime = bookingDateTime;
	}


	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Showtime getShowTime() {
		return showTime;
	}

	public void setShowTime(Showtime showTime) {
		this.showTime = showTime;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}

}
