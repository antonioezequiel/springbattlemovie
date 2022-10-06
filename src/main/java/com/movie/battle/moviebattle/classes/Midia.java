package com.movie.battle.moviebattle.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
	
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Title",
    "Year",
    "imdbRating",
    "imdbVotes",
    "imdbID"
})
public class Midia {
	@Id
	@JsonProperty("imdbID")
	private String imdbId;
	
	@JsonProperty("Title")
	private String title;
	@Column(name = "ano")
	@JsonProperty("Year")
	private String year;
	 
	@JsonProperty("imdbRating")
	private Double rating;
	 
	@JsonProperty("imdbVotes")
	private String votes;
	 
	private double score;
    
	private String foto;
	@ManyToOne
	private Categoria categoria;
	
	public Midia(String imdbId, String title, String year, Double rating, String votes) {
		super();	
		this.imdbId = imdbId;
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.votes = votes;
		this.score = rating * Double.parseDouble(votes);
	}
	
	public Midia() {}

	public Midia(String imdbId, String title, String year) {
		this.imdbId = imdbId;
		this.title = title;
		this.year = year;
	}

	/**
	 * @return the imdbId
	 */
	public String getImdbId() {
		return imdbId;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	/**
	 * @param imdbId the imdbId to set
	 */
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
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
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}

	/**
	 * @return the votes
	 */
	public String getVotes() {
		return votes;
	}

	/**
	 * @param votes the votes to set
	 */
	public void setVotes(String votes) {
		this.votes = votes;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Media [imdbId=" + imdbId + ", title=" + title + ", year=" + year + ", rating=" + rating + ", votes="
				+ votes + ", score=" + score + ", foto=" + foto + ", categoria=" + categoria + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
