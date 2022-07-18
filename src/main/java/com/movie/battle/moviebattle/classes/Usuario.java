package com.movie.battle.moviebattle.classes;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Usuario {
	@Id
	@Column(unique = true, length = 50)
	private String nome;
	private String senha;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles =  new ArrayList<Role>();
	
	public Usuario(String nome, String senha, List<Role> roles) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.roles = roles;
	}
	public Usuario(String nome, String senha) {
		super();
		this.nome = nome;
		this.senha = senha;
	}
	
	public Usuario() {}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
