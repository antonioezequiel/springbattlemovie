package com.movie.battle.moviebattle.repository;

import java.util.List;

import com.movie.battle.moviebattle.classes.Jogo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JogoRepository extends JpaRepository<Jogo, Integer>{
	
	@Query(value="SELECT * FROM \"jogo\" Order by \"score\" desc LIMIT :inicio, :quant", nativeQuery = true)
	public List<Jogo> findAllWithPagination(@Param("inicio") int inicio,
													@Param("quant")int quant);
	
	//Page<Jogo> findAll(Pageable pageable);
}
