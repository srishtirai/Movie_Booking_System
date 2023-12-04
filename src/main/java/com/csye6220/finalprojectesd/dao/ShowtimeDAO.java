package com.csye6220.finalprojectesd.dao;

import java.util.List;

import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.Theater;

public interface ShowtimeDAO {
	public void saveShowtime(Showtime showtime);
	public Showtime getShowtimeById(Long id);
	public List<Showtime> getAllShowtimes();
	public List<Showtime> getAllShowtimesByMovie(Movie movie);
	public List<Showtime> getAllShowtimesByTheater(Theater theater);
    public void updateShowtime(Showtime showtime);
    public void deleteShowtime(Showtime showtime);
}
