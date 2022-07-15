package com.movie.battle.moviebattle.DTO;

public class UserRankingDTO {
	private Integer id;
	private String usuario;
	private Integer score;
	
	public UserRankingDTO(int id, String usuario, int score) {
		this.id = id;
		this.usuario = usuario;
		this.score = score;		
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	
	
}
