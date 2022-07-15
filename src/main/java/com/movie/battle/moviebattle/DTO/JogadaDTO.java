package com.movie.battle.moviebattle.DTO;


public class JogadaDTO {
	private String imdbID;

	public JogadaDTO() {}
	
	public JogadaDTO(String imdbID) {
		super();
		this.imdbID = imdbID;
	}

	/**
	 * @return the imdbID
	 */
	public String getImdbID() {
		return imdbID;
	}

	/**
	 * @param imdbID the imdbID to set
	 */
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
	
 }
