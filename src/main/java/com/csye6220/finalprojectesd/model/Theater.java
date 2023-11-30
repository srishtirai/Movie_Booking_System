package com.csye6220.finalprojectesd.model;

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
    private String openingTime;
    
    @Column(name="closing_time")
    private String closingTime;
    
    private int capacity;

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

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
    
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
    
}
