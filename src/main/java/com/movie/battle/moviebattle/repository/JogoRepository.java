package com.movie.battle.moviebattle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.movie.battle.moviebattle.classes.Jogo;


public interface JogoRepository extends JpaRepository<Jogo, Integer>{
	
	@Query(value="SELECT * FROM \"jogo\" Order by \"score\" desc LIMIT :inicio, :quant", nativeQuery = true)
	public List<Jogo> findAllWithPagination(@Param("inicio") int inicio,
													@Param("quant")int quant);

}
