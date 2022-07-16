package com.movie.battle.moviebattle.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.movie.battle.moviebattle.classes.Categoria;
import com.movie.battle.moviebattle.classes.Midia;

public interface MidiaRepository extends JpaRepository<Midia, String>{
	//@Query(value="SELECT * FROM Movie LIMIT 2", nativeQuery = true)
	//List<Movie> buscaDoisMovies();
	
	List<Midia> findByCategoria(Categoria categoria);
	

}
