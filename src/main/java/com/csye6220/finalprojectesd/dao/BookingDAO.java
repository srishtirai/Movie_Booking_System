package com.csye6220.finalprojectesd.dao;

import java.util.List;

import com.csye6220.finalprojectesd.model.Booking;

public interface BookingDAO {
	public void saveBooking(Booking booking);
	public Booking getBookingById(Long id);
	public Booking getBookingByUserId(Long userId);
	public List<Booking> getAllBookings();
    public void updateBooking(Booking booking);
    public void deleteBooking(Booking booking);
	public Long getBookingCountByShowtimeId(Long showtimeId);
}
