package com.csye6220.finalprojectesd.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "theaters")
public class Theater {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theater_id")
    private Long theaterId;
	
    private String name;
    
    private String location;
    
    @Column(name="opening_time")
    private LocalTime openingTime;
    
    @Column(name="closing_time")
    private LocalTime closingTime;
    
    @OneToMany(mappedBy = "theater", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Showtime> showtimes = new HashSet<>();

    public Theater() {
    }

    public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

	public Set<Showtime> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(Set<Showtime> showtimes) {
		this.showtimes = showtimes;
	}
   
}
