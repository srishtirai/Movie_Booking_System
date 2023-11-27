package com.csye6220.finalprojectesd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csye6220.finalprojectesd.dao.MovieDAO;
import com.csye6220.finalprojectesd.model.Genre;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Review;

@Service
public class MovieService {

	@Autowired
	private MovieDAO movieDAO;
	
	public void saveMovie(Movie movie) {
		movieDAO.saveMovie(movie);
	}
	
	public Movie getMovieById(Long id) {
		return movieDAO.getMovieById(id);
	}

	public List<Movie> getAllMovies() {
		return movieDAO.getAllMovies();
	}

	public void updateMovie(Movie movie) {
		movieDAO.updateMovie(movie);
	}

	public void deleteMovie(Long id) {
		movieDAO.deleteMovie(id);
	}

	public List<Movie> findByMovieName(String movieName) {
		return movieDAO.findByMovieName(movieName);
	}

	public List<Movie> findByGenre(Genre genre) {
		return movieDAO.findByGenre(genre);
	}

	public List<Review> findReviewsByMovie(Long id) {
		return movieDAO.findReviewsByMovie(id);
	}
	
}
