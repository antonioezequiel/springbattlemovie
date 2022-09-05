package com.movie.battle.moviebattle.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.movie.battle.moviebattle.DTO.UsuarioDTO;
import com.movie.battle.moviebattle.DTO.UsuarioSemSenhaDTO;
import com.movie.battle.moviebattle.classes.Role;
import com.movie.battle.moviebattle.classes.Usuario;
import com.movie.battle.moviebattle.classes.UsuarioAtenticacao;
import com.movie.battle.moviebattle.exception.CadastrarUsuarioException;
import com.movie.battle.moviebattle.repository.RoleRepository;
import com.movie.battle.moviebattle.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UsuarioService(ModelMapper modelMapper, UsuarioRepository usuarioRepository, RoleRepository roleRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}

	public void create(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public UsuarioAtenticacao loadUserByUsername(String nome) {
		Optional<Usuario> usuario = usuarioRepository.findById(nome);
		if (usuario.isPresent()) {
			return new UsuarioAtenticacao(usuario.get());
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

	public UsuarioSemSenhaDTO criarUsuario(Usuario usuario) throws CadastrarUsuarioException {
		Role role = roleRepository.getByNome("ADM");
		if (role == null) {
			role = new Role("ADM");
			roleRepository.save(role);
		}
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		usuario.setRoles(Arrays.asList(role));
		try {
			usuarioRepository.save(usuario);
			return transformaEmDTO(usuario);
		} catch (Exception e) {
			throw new CadastrarUsuarioException();
		}
	}

	public UsuarioSemSenhaDTO transformaEmDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioSemSenhaDTO.class);
	}

	public Usuario transformaParaUsuario(UsuarioDTO usuarioDTO) {
		return modelMapper.map(usuarioDTO, Usuario.class);
	}

}
