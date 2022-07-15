package com.movie.battle.moviebattle.classes;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
	
@Entity
public class Movie extends Media{

	public Movie() {}

	public Movie(String imdbId, String title, String year, Double rating, String votes) {
		super(imdbId, title, year, rating, votes);
	}

	public Movie(String imdbId, String title, String year) {
		super(imdbId, title, year);
	}
	
	/*@Id
	@JsonProperty("imdbID")
	private String imdbId;
	
	@JsonProperty("Title")
    private String title;

	@JsonProperty("Year")
    private String year;
	 
	@JsonProperty("imdbRating")
    private Double rating;
	 
	@JsonProperty("imdbVotes")
    private String votes;
	 
    private double score;
    
    private String foto;

	public Movie(String imdbId, String title, String year, Double rating, String votes) {
		super();	
		this.imdbId = imdbId;
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.votes = votes;
		this.score = rating * Double.parseDouble(votes);
	}
	
	public Movie() {}

	public Movie(String imdbId, String title, String year) {
		this.imdbId = imdbId;
		this.title = title;
		this.year = year;
	}
*/
	
}
