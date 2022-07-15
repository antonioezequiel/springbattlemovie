package com.movie.battle.moviebattle.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.battle.moviebattle.DTO.UserRankingDTO;
import com.movie.battle.moviebattle.classes.Jogo;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.JogoRepository;

@Service
public class JogosService {
	private final JogoRepository jogoRepository;

	@Autowired
	public JogosService(JogoRepository jogoRepository) {
		this.jogoRepository = jogoRepository;
	}

	public Jogo savePart(Partida partida) {
		Jogo jogo = new Jogo();
		jogo.setUsuario(partida.getUsuario());
		jogo.setScore(partida.getScore());
		jogo.setRound(partida.getRound());
		return jogoRepository.save(jogo);
	}

	public List<UserRankingDTO> getRanking(int pageInicio) {
		int quantidade = 3;
		int inicioListagem = (pageInicio-1)*quantidade;
		List<Jogo> usersRanking = jogoRepository.findAllWithPagination(inicioListagem, quantidade);
		List<UserRankingDTO> usersRankingDTO = new ArrayList<UserRankingDTO>();
		usersRanking.stream().forEach(jogo -> usersRankingDTO.add(jogo.obterUsuarioDTO()));
				
		return usersRankingDTO;
	}

}
