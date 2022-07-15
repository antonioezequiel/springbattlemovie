package com.movie.battle.moviebattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movie.battle.moviebattle.classes.Serie;


public interface SerieRepository extends JpaRepository<Serie, String>{
	//@Query(value="SELECT * FROM Movie LIMIT 2", nativeQuery = true)
	//List<Movie> buscaDoisMovies();

}
