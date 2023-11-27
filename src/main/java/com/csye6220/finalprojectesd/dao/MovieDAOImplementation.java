package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.csye6220.finalprojectesd.model.Genre;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Review;

@Component
public class MovieDAOImplementation implements MovieDAO {

	@Override
	public void saveMovie(Movie movie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Movie getMovieById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMovie(Movie movie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMovie(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Movie> findByMovieName(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> findByGenre(Genre genre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Review> findReviewsByMovie(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
