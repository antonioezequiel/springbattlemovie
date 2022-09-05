package com.movie.battle.moviebattle.service;

import java.util.ArrayList;
import java.util.List;

import com.movie.battle.moviebattle.DTO.UserRankingDTO;
import com.movie.battle.moviebattle.classes.Jogo;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.JogoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
		//0, 3 - 3 - 3
		
		//Pageable Pages = PageRequest.of(inicioListagem, quantidade);
		
		//Page<Jogo> usersRanking = jogoRepository.findAll(Pages);
		List<Jogo> usersRanking = jogoRepository.findAllWithPagination(inicioListagem, quantidade);
		
		List<UserRankingDTO> usersRankingDTO = new ArrayList<UserRankingDTO>();
		usersRanking.stream().forEach(jogo -> usersRankingDTO.add(jogo.obterUsuarioDTO()));
				
		return usersRankingDTO;
	}

}
