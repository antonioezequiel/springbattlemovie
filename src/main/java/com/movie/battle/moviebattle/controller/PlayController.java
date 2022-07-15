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
import com.movie.battle.moviebattle.DTO.MovieDTO;
import com.movie.battle.moviebattle.DTO.PartidaDTO;
import com.movie.battle.moviebattle.classes.Jogo;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.service.JogosService;
import com.movie.battle.moviebattle.service.MovieService;
import com.movie.battle.moviebattle.service.PartidaService;

@RestController
@RequestMapping("/api/movieshow")
public class PlayController {
	private final MovieService movieService;
	private final PartidaService partidaService;
	private final JogosService jogosService;

	@Autowired
	public PlayController(MovieService movieService, PartidaService partidaService, JogosService jogosService) {
		this.movieService = movieService;
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
		Partida partida;
		try {
			partida = partidaService.recuperarDadosPartidaUsuario(request);
			if (!partida.isJogadaAtiva()) {
				return ResponseEntity.ok(movieService.sortearFilmes(partida));				
			} else {
				return ResponseEntity.ok(MovieDTO.transformaEmListaMoviesDTO(partida.getMovies()));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/jogar")
	public ResponseEntity<?> jogar(@RequestBody JogadaDTO jogadaDTO, HttpServletRequest request) {
		Partida partida;
		PartidaDTO partidaDTO;
		try {
			partida = partidaService.recuperarDadosPartidaUsuario(request);
			if (partida.isJogadaAtiva()) {
				partida = partida.verifyAnswer(jogadaDTO);
				partidaDTO = PartidaDTO.transformaEmDTO(partida);
				partidaService.removerDadosMovieJogadaDa(partida);
				if (!partida.existeVidas()) {
					finalizarJogo(partida);
					partidaDTO.setMessage("O jogo acabou. Você não tem mais vidas. Dados da partida salvos com sucesso");
				}
			} else {
				partidaDTO = PartidaDTO.transformaEmDTO(partida);
				partidaDTO.setMessage("É necessário realizar o sorteio dos filmes");
			}
			return ResponseEntity.ok(partidaDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/finalizar")
	public ResponseEntity<PartidaDTO> finalizar(HttpServletRequest request) {
		Partida partida;
		try {
			partida = partidaService.recuperarDadosPartidaUsuario(request);
			finalizarJogo(partida);
			PartidaDTO partidaDTO = PartidaDTO.transformaEmDTO(partida);
			partidaDTO.setMessage("O jogo acabou. Dados da partida salvos com sucesso");
			return ResponseEntity.ok(partidaDTO);
		}catch (Exception e) {
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

	private Jogo finalizarJogo(Partida partida) {
		partidaService.removerDadosDa(partida);
		return jogosService.savePart(partida);
	}
}
