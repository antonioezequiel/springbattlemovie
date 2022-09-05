package com.movie.battle.moviebattle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.movie.battle.moviebattle.classes.Jogo;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.JogoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JogosServiceTest {
	@Mock
	JogoRepository jogoRepository;
	
	JogosService jogosService;
	
	@BeforeEach
	public void beforeEach() throws IOException {
		MockitoAnnotations.openMocks(this);
		this.jogosService = new JogosService(jogoRepository);
	}
	
	@Test
	void salvarJogo() {
		Jogo jogo = new Jogo("Antonio", 2, 10);
		Mockito.when(jogoRepository.save(Mockito.any())).thenReturn(jogo);
		Jogo jo = jogosService.savePart(new Partida("Antonio", 10, 2, 10));
		assertEquals(jogo, jo);
		System.out.println(jogoRepository.count());
	}

}
