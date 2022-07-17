package com.movie.battle.moviebattle.service;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.battle.moviebattle.DTO.JogadaDTO;
import com.movie.battle.moviebattle.DTO.PartidaDTO;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.PartidaRepository;
import com.movie.battle.moviebattle.security.AutenticationTokenFilter;

@Service
public class PartidaService {
	private final TokenService tokenService;
	private final PartidaRepository partidaRepository;
	private final ModelMapper modelMapper;
	private final JogosService jogosService;

	@Autowired
	public PartidaService(JogosService jogosService, TokenService tokenService, PartidaRepository partidaRepository,
			ModelMapper modelMapper) {
		this.tokenService = tokenService;
		this.partidaRepository = partidaRepository;
		this.modelMapper = modelMapper;
		this.jogosService = jogosService;
	}

	public PartidaDTO configurarDadosPartida(HttpServletRequest request) {
		String token = recuperarToken(request);
		PartidaDTO partidaDTO = new PartidaDTO();
		partidaDTO.setUsuario(tokenService.getNomeUsuario(token));
		partidaDTO.setScore(0);
		partidaDTO.setLife(3);
		partidaDTO.setRound(0);
		partidaDTO.setMessage("Jogo Iniciado, sortei as Cartas!");

		Partida partida = modelMapper.map(partidaDTO, Partida.class);
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
		} else {
			throw new Exception("Partida não localizada");
		}
	}

	public void atualizarPartida(Partida partida) {
		partidaRepository.save(partida);
	}

	/*
	 * Atualiza a lista de filmes sorteados para vazia
	 */
	public void removerDadosMidiaJogadaDa(Partida partida) {
		partida.removeMidiasJogada();

		/* Atualiza partida sem midias, permitindo a escolha de novas mídias */
		partidaRepository.save(partida);
	}

	/*
	 * Apaga dados temporários da partida
	 */
	public void removerDadosDa(Partida partida) {
		partidaRepository.deleteById(partida.getToken());
	}

	public PartidaDTO converteParaDTO(Partida partida) {
		return modelMapper.map(partida, PartidaDTO.class);
	}

	public PartidaDTO analisarResposta(Partida partida, JogadaDTO jogadaDTO) {
		PartidaDTO partidaDTO;
		//Se a jogada estiver ativa, ou seja, se teve mídias sorteadas
		if (partida.isJogadaAtiva()) {
			partida = partida.verifyAnswer(jogadaDTO.getImdbID());
			partidaDTO = converteParaDTO(partida);
			removerDadosMidiaJogadaDa(partida);

			if (!partida.existeVidas()) {
				partidaDTO = finalizarPartida(partida,
						"O jogo acabou. Você não tem mais vidas. Dados da partida salvos com sucesso");
			}
		}else {
			partidaDTO = converteParaDTO(partida);
			partidaDTO.setMessage("É necessário realizar o sorteio dos Midias");
		}
		return partidaDTO;
	}

	public PartidaDTO finalizarPartida(Partida partida, String mensagem) {
		removerDadosDa(partida);
		jogosService.savePart(partida);
		PartidaDTO partidaDTO = converteParaDTO(partida);
		partidaDTO.setMessage(mensagem);
		return partidaDTO;
	}
}
