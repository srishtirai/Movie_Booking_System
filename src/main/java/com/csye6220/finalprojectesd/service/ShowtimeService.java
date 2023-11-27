package com.csye6220.finalprojectesd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csye6220.finalprojectesd.dao.ShowtimeDAO;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;

@Service
public class ShowtimeService {

	@Autowired
	private ShowtimeDAO showtimeDAO;
	
	public void saveShowtime(Showtime showtime) {
		showtimeDAO.saveShowtime(showtime);
	}
	
	public List<Showtime> getAllShowtimesByMovie(Movie movie) {
		return showtimeDAO.getAllShowtimesByMovie(movie);
	}
	
    public void updateShowtime(Showtime showtime) {
    	showtimeDAO.updateShowtime(showtime);
    }
    
    public void deleteShowtime(Showtime showtime) {
    	showtimeDAO.deleteShowtime(showtime);
    }
	
}
