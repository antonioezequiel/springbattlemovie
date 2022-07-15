package com.movie.battle.moviebattle.DTO;

import com.movie.battle.moviebattle.classes.Usuario;

public class UsuarioDTO {
	private String nome;
	private String senha;
	
	public UsuarioDTO() {}

	public UsuarioDTO(String nome, String senha) {
		super();
		this.nome = nome;
		this.senha = senha;
	}
	
	public UsuarioDTO(String nome) {
		super();
		this.nome = nome;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario transformaParaUsuario() {
		return new Usuario(this.nome, this.senha);
	}

	public static UsuarioDTO transformaDTO(Usuario usuario) {
		return new UsuarioDTO(usuario.getNome());
	}
	
	
}
