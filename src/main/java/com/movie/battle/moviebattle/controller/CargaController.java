package com.movie.battle.moviebattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.battle.moviebattle.DTO.UsuarioDTO;
import com.movie.battle.moviebattle.classes.Usuario;
import com.movie.battle.moviebattle.service.MovieService;
import com.movie.battle.moviebattle.service.UsuarioService;

@RestController
public class CargaController {
	private final MovieService movieService;
	private final UsuarioService usuarioService;
	
	@Autowired
	public CargaController(MovieService movieService, UsuarioService usuarioService) {
		super();
		this.movieService = movieService;
		this.usuarioService = usuarioService;
	}

	@GetMapping("/carregar")
	public String carregarDados() {
		movieService.carregarFilmesIMDB();
		return "dados carregados com sucesso";
	}
	
	@PostMapping("/create/new-user")
	public ResponseEntity<?> cadastrar(@RequestBody UsuarioDTO usuarioDTO){
		try {
			Usuario usuario = usuarioService.criarUsuario(usuarioDTO.transformaParaUsuario());
			return ResponseEntity.ok(UsuarioDTO.transformaDTO(usuario));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/create/new-user-padrao")
	public ResponseEntity<?> cadastrar(){
		try {
			usuarioService.criarUsuario();
			return ResponseEntity.ok("usuários padrão criados");
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.noContent().build();
		}
	}
	
	
}
