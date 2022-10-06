package com.movie.battle.moviebattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.battle.moviebattle.DTO.UsuarioDTO;
import com.movie.battle.moviebattle.DTO.UsuarioSemSenhaDTO;
import com.movie.battle.moviebattle.enums.TipoArquivoProposta;
import com.movie.battle.moviebattle.exception.CadastrarUsuarioException;
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
	
	@GetMapping("/tipos")
	public String tipos() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(TipoArquivoProposta.APENDICE));
		return "dados carregados com sucesso";
	}

	@PostMapping("/create/new-user")
	public ResponseEntity<?> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			UsuarioSemSenhaDTO usuarioSemSenhaDTO = usuarioService
					.criarUsuario(usuarioService.transformaParaUsuario(usuarioDTO));
			return ResponseEntity.ok(usuarioSemSenhaDTO);
		} catch (CadastrarUsuarioException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "usuário não foi cadastrado", e);
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
