package com.movie.battle.moviebattle.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.movie.battle.moviebattle.DTO.MidiaDTO;
import com.movie.battle.moviebattle.classes.Midia;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.MidiaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MidiaServiceTest {
	@Mock
	MidiaService midiaService;
	@Mock
	CategoriaService categoriaService;
	@Autowired
	ModelMapper modelMapper;
	@Mock
	PartidaService partidaService;
	@Mock
	MidiaRepository midiaRepository;
	
	@BeforeEach
	public void beforeEach() throws IOException {
		MockitoAnnotations.openMocks(this);
		this.midiaService = new MidiaService(categoriaService, modelMapper, midiaRepository, partidaService);
	}

	@Test
	void sortearMidiasTest() {
		List<Midia> midias = gerarListMedias();
		Mockito.when(midiaRepository.findByCategoria(null)).thenReturn(midias);
		List<MidiaDTO> midias2 = midiaService.sortearMidias(gerarPartida());
		assertNotEquals(midias2.get(0), midias2.get(1));
	}
	
	private List<Midia> gerarListMedias() {
		List<Midia> midias = new ArrayList<Midia>();	
		Midia midia = new Midia("1", "O amor é lindo", "2016", 12352.6, "225");
		midias.add(midia);
		midia = new Midia("2", "O amor está no ar", "2010", 352.6, "25");
		midias.add(midia);
		midia = new Midia("3", "coração valente", "2010", 3336.6, "65");
		midias.add(midia);
		midia = new Midia("4", "a casa da mãe joana", "2007", 1352.6, "36");
		midias.add(midia);
		return midias;
	}
	
	private Partida gerarPartida() {
		return new Partida("Antonio", 0, 3, 0);		
	}
}
