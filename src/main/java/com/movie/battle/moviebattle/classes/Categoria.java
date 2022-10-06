package com.movie.battle.moviebattle.classes;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String descricao;
	@OneToMany
	private List<Midia> medias;
	
	public Categoria(String descricao) {
		super();
		this.descricao = descricao;
	}
	
	public Categoria() {}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Midia> getMedias() {
		return medias;
	}

	public void setMedias(List<Midia> medias) {
		this.medias = medias;
	}
}
