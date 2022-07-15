package com.movie.battle.moviebattle.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.ManyToAny;
import com.movie.battle.moviebattle.DTO.JogadaDTO;

@Entity
public class Partida {
	@Id
	private String token;
	private String usuario;
    private int score;
    private Integer life;
    private int round;
    @ManyToAny(fetch = FetchType.LAZY, metaColumn = @Column)
    private List<Media> medias;
    @Transient
    private String message;
    
        
	public Partida(String usuario, int score, Integer life, int round) {
		super();
		this.usuario = usuario;
		this.score = score;
		this.life = life;
		this.round = round;
		this.medias = new ArrayList<Media>();
	}
	public Partida() {}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}	
	
	/**
	 * @return the movies
	 */
	public List<Media> getMedias() {
		return medias;
	}
	/**
	 * @param movies the movies to set
	 */
	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}
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
	
	public boolean isJogadaAtiva() {
		return !this.medias.isEmpty();
	}
	
	public Partida verifyAnswer(JogadaDTO jogadaDTO) {
		
		List<Media> mediasOrder = this.medias.stream().sorted(
									Comparator.comparing(Media::getScore).reversed())
									.collect(Collectors.toList());
		
		if (mediasOrder.get(0).getImdbId().equals(jogadaDTO.getImdbID())) {
			this.setScore(this.getScore() + 1);
			this.message = "Parabéns, você acertou";
		} else {
			this.setLife(this.getLife() - 1);
			this.message = "Infelizmente você acertou";
		}
		this.setRound(this.getRound() + 1);
		return this;		
	}
	
	public boolean existeVidas() {
		return this.getLife() > 0 ? true : false;
	}       
 }
