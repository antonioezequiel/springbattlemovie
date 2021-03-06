package com.movie.battle.moviebattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.battle.moviebattle.DTO.UsuarioDTO;
import com.movie.battle.moviebattle.DTO.UsuarioSemSenhaDTO;
import com.movie.battle.moviebattle.service.MidiaService;
import com.movie.battle.moviebattle.service.UsuarioService;

@RestController
public class CargaController {
	private final MidiaService midiaService;
	private final UsuarioService usuarioService;
	/*novo comentário*/

	@Autowired
	public CargaController(MidiaService midiaService, UsuarioService usuarioService) {
		super();
		this.midiaService = midiaService;
		this.usuarioService = usuarioService;
	}

	@GetMapping("/carregar")
	public String carregarDados() {
		midiaService.carregarMidiasIMDB();
		return "dados carregados com sucesso";
	}

	@PostMapping("/create/new-user")
	public ResponseEntity<?> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			UsuarioSemSenhaDTO usuarioSemSenhaDTO = usuarioService
					.criarUsuario(usuarioService.transformaParaUsuario(usuarioDTO));
			return ResponseEntity.ok(usuarioSemSenhaDTO);
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/create/new-user-padrao")
	public ResponseEntity<?> cadastrar() {
		try {
			usuarioService.criarUsuario();
			return ResponseEntity.ok("usuários padrão criados");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.noContent().build();
		}
	}

}
