package com.movie.battle.moviebattle.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.battle.moviebattle.DTO.PartidaDTO;
import com.movie.battle.moviebattle.classes.Movie;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.PartidaRepository;
import com.movie.battle.moviebattle.security.AutenticationTokenFilter;


@Service
public class PartidaService {
	TokenService tokenService;
	PartidaRepository partidaRepository;
	
	@Autowired
	public PartidaService(TokenService tokenService, PartidaRepository partidaRepository) {
		this.tokenService = tokenService;
		this.partidaRepository = partidaRepository;
	}
	
	public PartidaDTO configurarDadosPartida(HttpServletRequest request) {
		String token = recuperarToken(request);
		PartidaDTO partidaDTO = new PartidaDTO();
		partidaDTO.setUsuario(tokenService.getNomeUsuario(token));
		partidaDTO.setScore(0);
		partidaDTO.setLife(3);
		partidaDTO.setRound(0);
		partidaDTO.setMessage("Jogo Iniciado, sortei as Cartas!");
		
		Partida partida  = partidaDTO.transformaParaObjeto();
		partida.setToken(token);
		
		partidaRepository.save(partida);
			
		return partidaDTO;
	}

	private String recuperarToken(HttpServletRequest request) {
		return AutenticationTokenFilter.recuperarToken(request);
	}

	public Partida recuperarDadosPartidaUsuario(HttpServletRequest request) throws Exception {
		String token = recuperarToken(request);
		Optional<Partida> partida = partidaRepository.findById(token);
		if (partida.isPresent()) {
			return partida.get();
		}else {
			throw new Exception("Partida não localizada");
		}
	}

	public void atualizarPartida(Partida partida) {
		partidaRepository.save(partida);		
	}
	
	/*
	 * Atualiza a lista de filmes sorteados para vazia
	 */
	public void removerDadosMovieJogadaDa(Partida partida) {
		partida.setMovies(new ArrayList<Movie>());
		partidaRepository.save(partida);		
	}
	
	/*
	 * Apaga dados temporários da partida
	 */
	public void removerDadosDa(Partida partida) {
		partidaRepository.deleteById(partida.getToken());		
	}

}
