package com.movie.battle.moviebattle.classes;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
	
@Entity
public class Serie extends Media{

	public Serie() {}

	public Serie(String imdbId, String title, String year, Double rating, String votes) {
		super(imdbId, title, year, rating, votes);
	}

	public Serie(String imdbId, String title, String year) {
		super(imdbId, title, year);
	}
	
}
