package com.movie.battle.moviebattle.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.movie.battle.moviebattle.classes.Role;
import com.movie.battle.moviebattle.classes.Usuario;
import com.movie.battle.moviebattle.repository.RoleRepository;
import com.movie.battle.moviebattle.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	UsuarioRepository usuarioRepository;
	RoleRepository roleRepository;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
	}

	public void create(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario loadUserByUsername(String nome) {
		Optional<Usuario> usuario = usuarioRepository.findById(nome);
		if (usuario.isPresent()) {
			return usuario.get();
		} else
			throw new UsernameNotFoundException("O usuário " + nome + " não foi encontrado");
	}

	/*
	 * cria usuários básicos para usar o sistema
	 */
	public void criarUsuario() {
		Usuario usuario = new Usuario();
		Role role = new Role("ADM");
		roleRepository.save(role);
		// usuário antonio, senha:123456
		usuario.setNome("antonio");
		usuario.setSenha("$2a$10$HXbO5MpTbnfxOwgecC0BguLnPr36waVWtJdbQ0Xhor4YZipiuku3a");
		usuario.setRoles(Arrays.asList());
		usuarioRepository.save(usuario);

		// usuário jose, senha: 123456
		usuario.setNome("jose");
		usuario.setSenha("$2a$10$HXbO5MpTbnfxOwgecC0BguLnPr36waVWtJdbQ0Xhor4YZipiuku3a");
		usuario.setRoles(Arrays.asList(new Role("ADM")));
		usuarioRepository.save(usuario);
	}

	public Usuario criarUsuario(Usuario usuario) {
		Role role = roleRepository.getByNome("ADM");
		if (role == null) {
			role = new Role("ADM");
			roleRepository.save(role);
		}
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		usuario.setRoles(Arrays.asList(role));
		try {
			usuarioRepository.save(usuario);
			return usuario;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
