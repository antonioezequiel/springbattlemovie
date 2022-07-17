package com.movie.battle.moviebattle.DTO;

public class PartidaDTO {
	private String usuario;
    private int score;
    private Integer life;
    private int round;
    private String message;
    
	public PartidaDTO(String usuario, int score, Integer life, int round, String message) {
		super();
		this.usuario = usuario;
		this.score = score;
		this.life = life;
		this.round = round;
		this.message = message;
	}
	public PartidaDTO(String usuario, int score, Integer life, int round) {
		super();
		this.usuario = usuario;
		this.score = score;
		this.life = life;
		this.round = round;
	}
	
	public PartidaDTO() {}
	
	
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the life
	 */
	public Integer getLife() {
		return life;
	}
	/**
	 * @param life the life to set
	 */
	public void setLife(Integer life) {
		this.life = life;
	}
	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}
	/**
	 * @param round the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}       
 }
