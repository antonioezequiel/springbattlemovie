package com.movie.battle.moviebattle.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.movie.battle.moviebattle.DTO.UserRankingDTO;

@Entity
public class Jogo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String usuario;
    private int score;
    private int round;    
        
	public Jogo(String usuario, int score, int round) {
		super();
		this.usuario = usuario;
		this.score = score;
		this.round = round;
	}
	public Jogo() {}
	
			
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
	public UserRankingDTO obterUsuarioDTO() {
		return new UserRankingDTO(id, usuario, score);
	}  
 }
