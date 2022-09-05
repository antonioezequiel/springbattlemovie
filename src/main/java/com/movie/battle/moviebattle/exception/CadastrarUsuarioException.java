package com.movie.battle.moviebattle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "usuário não foi cadastrado")
public class CadastrarUsuarioException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
