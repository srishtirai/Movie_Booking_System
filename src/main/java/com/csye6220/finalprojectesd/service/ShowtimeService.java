package com.csye6220.finalprojectesd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csye6220.finalprojectesd.dao.ShowtimeDAO;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.Theater;

@Service
public class ShowtimeService {

	@Autowired
	private ShowtimeDAO showtimeDAO;
	
	public void saveShowtime(Showtime showtime) {
		showtimeDAO.saveShowtime(showtime);
	}
	
	public Showtime getShowtimeById(Long id) {
		return showtimeDAO.getShowtimeById(id);
	}
	
	public List<Showtime> getAllShowtimes() {
		return showtimeDAO.getAllShowtimes();
	}
	
	public List<Showtime> getAllShowtimesByMovie(Movie movie) {
		return showtimeDAO.getAllShowtimesByMovie(movie);
	}
	
	public List<Showtime> getAllShowtimesByTheater(Theater theater) {
		return showtimeDAO.getAllShowtimesByTheater(theater);
	}
	
    public void updateShowtime(Showtime showtime) {
    	showtimeDAO.updateShowtime(showtime);
    }
    
    public void deleteShowtime(Showtime showtime) {
    	showtimeDAO.deleteShowtime(showtime);
    }
	
}
