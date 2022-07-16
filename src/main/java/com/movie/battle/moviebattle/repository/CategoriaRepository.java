package com.movie.battle.moviebattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movie.battle.moviebattle.classes.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	//@Query(value="SELECT * FROM Movie LIMIT 2", nativeQuery = true)
	//List<Movie> buscaDoisMovies();
	
	 Categoria findByDescricao(String descricao);

}
