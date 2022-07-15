package com.movie.battle.moviebattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movie.battle.moviebattle.classes.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

}
