package com.csye6220.finalprojectesd.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "theaters")
public class Theater {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theater_id")
    private Long theaterId;
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @Column(name = "opening_time")
    @NotNull(message = "Opening time is required")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    @NotNull(message = "Closing time is required")
    private LocalTime closingTime;
    
    @AssertTrue(message = "Opening time should be before closing time")
    public boolean getIsOpeningTimeBeforeClosingTime() {
        return (openingTime != null && closingTime != null) && openingTime.isBefore(closingTime);
    }
    
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
//   
//    @Target({ElementType.FIELD})
//    @Retention(RetentionPolicy.RUNTIME)
//    @Constraint(validatedBy = ClosingTimeAfterOpeningTimeValidator.class)
//    @Documented
//    public @interface ClosingTimeAfterOpeningTime {
//        String message() default "Closing time should be after opening time";
//        Class<?>[] groups() default {};
//        Class<? extends Payload>[] payload() default {};
//    }
//
//    class ClosingTimeAfterOpeningTimeValidator implements ConstraintValidator<ClosingTimeAfterOpeningTime, LocalTime> {
//
//        @Override
//        public boolean isValid(LocalTime closingTime, ConstraintValidatorContext context) {
//            return closingTime == null || closingTime.isAfter(openingTime);
//        }
//    }
}
