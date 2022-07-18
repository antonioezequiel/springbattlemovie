package com.movie.battle.moviebattle.classes;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioAtenticacao implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public UsuarioAtenticacao(Usuario usuario) {
		super();
		this.usuario = usuario;
	}
	
	public UsuarioAtenticacao() {}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.usuario.getRoles();
	}
	@Override
	public String getPassword() {
		return this.usuario.getSenha();
	}
	@Override
	public String getUsername() {
		return this.usuario.getNome();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
