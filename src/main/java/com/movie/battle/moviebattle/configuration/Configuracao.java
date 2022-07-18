package com.movie.battle.moviebattle.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.movie.battle.moviebattle.DTO.MidiaDTO;
import com.movie.battle.moviebattle.classes.Midia;

@Configuration
public class Configuracao {
	
	@Bean
	public ModelMapper modelMapper() {
		/*
		 * Mapeamento para definir apenas a descrição da categoria, como categoria no DTO
		 */
		PropertyMap<Midia, MidiaDTO> midiaMap = new PropertyMap<Midia, MidiaDTO>() {
			  protected void configure() {
			    map().setCategoria(source.getCategoria().getDescricao());
			  }
		};

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(midiaMap);
		
		return modelMapper;
	}
}
