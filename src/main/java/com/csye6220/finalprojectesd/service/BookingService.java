package com.csye6220.finalprojectesd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csye6220.finalprojectesd.dao.BookingDAO;
import com.csye6220.finalprojectesd.model.Booking;

@Service
public class BookingService {

	@Autowired
	private BookingDAO bookingDAO;

	public void saveBooking(Booking booking) {
		bookingDAO.saveBooking(booking);
	}

	public Booking getBookingById(Long id) {
		return bookingDAO.getBookingById(id);
	}

	public Booking getBookingByUserId(Long userId) {
		return bookingDAO.getBookingByUserId(userId);
	}

	public List<Booking> getAllBookings() {
		return bookingDAO.getAllBookings();
	}

	public void updateBooking(Booking booking) {
		bookingDAO.updateBooking(booking);
	}

	public void deleteBooking(Booking booking) {
		bookingDAO.deleteBooking(booking);
	}
	
	public Long getBookingCountByShowtimeId(Long showtimeId) {
		return bookingDAO.getBookingCountByShowtimeId(showtimeId);
	}
    
}
