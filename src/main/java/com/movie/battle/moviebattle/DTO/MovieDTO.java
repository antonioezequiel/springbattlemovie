package com.movie.battle.moviebattle.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.movie.battle.moviebattle.classes.Movie;

public class MovieDTO {
	private String title;
	private String year;
	private String imdbId;
	
	public MovieDTO(String title, String year, String imdbId) {
		super();
		this.title = title;
		this.year = year;
		this.imdbId = imdbId;
	}

	public static MovieDTO transformaEmDTO(Movie movie) {
		return new MovieDTO(movie.getTitle(), movie.getYear(), movie.getImdbId());
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the imdbId
	 */
	public String getImdbId() {
		return imdbId;
	}

	/**
	 * @param imdbId the imdbId to set
	 */
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public static List<Movie> transformaEmListaMovies(List<MovieDTO> moviesDTO) {
		return moviesDTO.stream()
				.map(movieDTO -> 
					new Movie(movieDTO.getImdbId(), movieDTO.getTitle(), movieDTO.getYear()))
						.collect(Collectors.toList());
	}
	
	public static List<MovieDTO> transformaEmListaMoviesDTO(List<Movie> movies) {
		return movies.stream()
				.map(movie -> 
					new MovieDTO(movie.getTitle(), movie.getYear(), movie.getImdbId()))
						.collect(Collectors.toList());
	}

}
