package com.csye6220.finalprojectesd.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movie_id")
    private Long movieId;
	
	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;
    
    private String description;
    
    @NotNull(message = "Release date is required")
    @Column(name="release_date")
    private LocalDate releaseDate;
    
    @Min(value = 10, message = "Duration must be at least 10 minutes")
    private int duration;
    
    @NotNull(message = "Release date is required")
    @Enumerated(EnumType.STRING)
    private Genre genre;
    
    private String cast;
    
    @NotBlank(message = "Language is required")
    private String language;
    
    @NotBlank(message = "Image URL is required")
    @URL(message = "Invalid URL format")
    @Column(name="image_url")
    private String imageUrl;
    
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Showtime> showtimes = new HashSet<>();

    public Movie() {
    }

	public Movie(Long movieId, String title, String description, LocalDate releaseDate, int duration, Genre genre,
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

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
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
	
	public Set<Showtime> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(Set<Showtime> showtimes) {
		this.showtimes = showtimes;
	}
    
}
