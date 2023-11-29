package com.csye6220.finalprojectesd.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
	
    private String title;
    
    private String description;
    
    private LocalDateTime releaseDate;
    
    private int duration;
    
    @Enumerated(EnumType.STRING)
    private Genre genre;
    
    private String cast;
    
    private String language;
    
    private String imageUrl;

    public Movie() {
    }
    
	public Movie(Long movieId, String title, String description, LocalDateTime releaseDate, int duration, Genre genre,
			String cast, String language, String imageUrl) {
		this.movieId = movieId;
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.duration = duration;
		this.genre = genre;
		this.cast = cast;
		this.language = language;
		this.imageUrl = imageUrl;
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

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDateTime releaseDate) {
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
}
