package com.movie.battle.moviebattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movie.battle.moviebattle.classes.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
	Role getByNome(String string);
}
