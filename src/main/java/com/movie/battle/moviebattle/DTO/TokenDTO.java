package com.movie.battle.moviebattle.DTO;

public class TokenDTO {
	
	private String token;
	private String tipo;
	
	public TokenDTO(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

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

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
