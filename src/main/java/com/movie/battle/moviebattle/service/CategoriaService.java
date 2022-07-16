package com.movie.battle.moviebattle.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movie.battle.moviebattle.classes.Categoria;
import com.movie.battle.moviebattle.repository.CategoriaRepository;

@Service
public class CategoriaService {
	private final CategoriaRepository categoriaRepository;

	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	public Optional<Categoria> findByDescricao(String descricao) {
		return Optional.ofNullable(categoriaRepository.findByDescricao(descricao));
	}
	
	public void criarCategoriasPadrao() {
		Categoria c = new Categoria("Filme");
		categoriaRepository.save(c);
		
		Categoria c2 = new Categoria("Serie");
		categoriaRepository.save(c2);
	}

	public Categoria buscarCategoriaAleatoria() {
		List<Categoria> categorias = categoriaRepository.findAll();
		//List<Integer> ids = categorias.stream().map(Categoria::getId).collect(Collectors.toList());
		Collections.shuffle(categorias);
		return categorias.get(0);
	}
}
