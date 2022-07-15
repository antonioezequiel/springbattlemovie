package com.movie.battle.moviebattle.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.movie.battle.moviebattle.classes.Movie;
import com.movie.battle.moviebattle.classes.Serie;

public class SerieDTO {
	private String title;
	private String year;
	private String imdbId;
	
	public SerieDTO(String title, String year, String imdbId) {
		super();
		this.title = title;
		this.year = year;
		this.imdbId = imdbId;
	}

	public static SerieDTO transformaEmDTO(Serie serie) {
		return new SerieDTO(serie.getTitle(), serie.getYear(), serie.getImdbId());
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

	public static List<Serie> transformaEmListaMovies(List<SerieDTO> seriesDTO) {
		return seriesDTO.stream()
				.map(serieDTO -> 
					new Serie(serieDTO.getImdbId(), serieDTO.getTitle(), serieDTO.getYear()))
						.collect(Collectors.toList());
	}
	
	public static List<SerieDTO> transformaEmListaMoviesDTO(List<Serie> series) {
		return series.stream()
				.map(serie -> 
					new SerieDTO(serie.getTitle(), serie.getYear(), serie.getImdbId()))
						.collect(Collectors.toList());
	}

}
