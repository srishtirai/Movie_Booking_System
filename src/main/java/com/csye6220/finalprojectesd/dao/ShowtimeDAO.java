package com.csye6220.finalprojectesd.dao;

import java.util.List;

import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;

public interface ShowtimeDAO {
	public void saveShowtime(Showtime showtime);
	public List<Showtime> getAllShowtimesByMovie(Movie movie);
    public void updateShowtime(Showtime showtime);
    public void deleteShowtime(Showtime showtime);
}
