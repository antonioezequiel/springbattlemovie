package com.movie.battle.moviebattle.DTO;

public class UsuarioSemSenhaDTO {
	private String nome;
	
	public UsuarioSemSenhaDTO() {}

	public UsuarioSemSenhaDTO(String nome) {
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


//	public Usuario transformaParaUsuario() {
//		return new Usuario(this.nome, this.senha);
//	}

//	public static UsuarioDTO transformaDTO(Usuario usuario) {
//		return new UsuarioDTO(usuario.getNome());
//	}
	
	
}
