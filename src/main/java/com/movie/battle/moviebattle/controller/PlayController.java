package com.movie.battle.moviebattle.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.movie.battle.moviebattle.DTO.JogadaDTO;
import com.movie.battle.moviebattle.DTO.PartidaDTO;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.service.JogosService;
import com.movie.battle.moviebattle.service.MidiaService;
import com.movie.battle.moviebattle.service.PartidaService;

@RestController
@RequestMapping("/api/movieshow")
public class PlayController {
	private final MidiaService midiaService;
	private final PartidaService partidaService;
	private final JogosService jogosService;

	@Autowired
	public PlayController(MidiaService midiaService, PartidaService partidaService, JogosService jogosService) {
		this.midiaService = midiaService;
		this.partidaService = partidaService;
		this.jogosService = jogosService;
	}

	@GetMapping("/iniciar")
	public ResponseEntity<PartidaDTO> iniciar(HttpServletRequest request) {
		PartidaDTO partidaDTO = partidaService.configurarDadosPartida(request);
		return ResponseEntity.ok(partidaDTO);
	}

	@GetMapping("/sortear")
	public ResponseEntity<?> sortear(HttpServletRequest request) {
		try {
			Partida partida = partidaService.recuperarDadosPartidaUsuario(request);
			return ResponseEntity.ok(midiaService.sortearMidias(partida));
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/jogar")
	public ResponseEntity<?> jogar(@RequestBody JogadaDTO jogadaDTO, HttpServletRequest request) {
		try {
			Partida partida = partidaService.recuperarDadosPartidaUsuario(request);
			PartidaDTO partidaDTO = partidaService.analisarResposta(partida, jogadaDTO);
			return ResponseEntity.ok(partidaDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/finalizar")
	public ResponseEntity<PartidaDTO> finalizar(HttpServletRequest request) {
		try {
			Partida partida = partidaService.recuperarDadosPartidaUsuario(request);
			PartidaDTO partidaDTO = partidaService.finalizarPartida(partida,
					"O jogo acabou. Dados da partida salvos com sucesso");
			return ResponseEntity.ok(partidaDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/ranking")
	public ResponseEntity<?> ranking(@RequestParam String page) {
		try {
			return ResponseEntity.ok(jogosService.getRanking(Integer.parseInt(page)));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
