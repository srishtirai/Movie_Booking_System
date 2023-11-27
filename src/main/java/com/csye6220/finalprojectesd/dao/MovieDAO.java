package com.csye6220.finalprojectesd.dao;

import java.util.List;

import com.csye6220.finalprojectesd.model.Genre;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Review;

public interface MovieDAO {
	public void saveMovie(Movie movie);
	public Movie getMovieById(Long id);
	public List<Movie> getAllMovies();
    public void updateMovie(Movie movie);
    public void deleteMovie(Movie movie);
    public List<Movie> findByMovieName(String movieName);
    public List<Movie> findByGenre(Genre genre);
    public List<Review> findReviewsByMovie(Movie movie);
}
