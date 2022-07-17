package com.movie.battle.moviebattle.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.battle.moviebattle.DTO.TokenDTO;
import com.movie.battle.moviebattle.classes.LoginForm;
import com.movie.battle.moviebattle.service.TokenService;

@RestController
@RequestMapping("/auth")
public class LoginController {
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	
	@Autowired
	public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken loginDados = form.converterDados();
		try {
			Authentication authentication = authenticationManager.authenticate(loginDados);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
