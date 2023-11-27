package com.csye6220.finalprojectesd.model;

import java.util.Date;

public class Movie {
    private Long movieId;
    private String title;
    private String description;
    private Date releaseDate;
    private int duration;
    private Genre genre;
    private String cast;
    private String language;
    private String iamgeUrl;

    public Movie() {
    }

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIamgeUrl() {
		return iamgeUrl;
	}

	public void setIamgeUrl(String iamgeUrl) {
		this.iamgeUrl = iamgeUrl;
	}
    
}
