package com.movie.battle.moviebattle.DTO;

import java.util.List;
import java.util.stream.Collectors;
import com.movie.battle.moviebattle.classes.Midia;

public class MidiaDTO {
	private String title;
	private String year;
	private String imdbId;
	private String categoria;
	private String foto;
	
	public MidiaDTO(String title, String year, String imdbId, String categoria, String foto) {
		super();
		this.title = title;
		this.year = year;
		this.imdbId = imdbId;
		this.categoria = categoria;
		this.foto = foto;
	}

	public static MidiaDTO transformaEmDTO(Midia media) {
		return new MidiaDTO(media.getTitle(), media.getYear(), media.getImdbId(), media.getCategoria().getDescricao(), media.getFoto());
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the imdbId
	 */
	public String getImdbId() {
		return imdbId;
	}

	/**
	 * @param imdbId the imdbId to set
	 */
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public static List<Midia> transformaEmListaMidias(List<MidiaDTO> mediasDTO) {
		return mediasDTO.stream()
				.map(mediaDTO -> 
					new Midia(mediaDTO.getImdbId(), mediaDTO.getTitle(), mediaDTO.getYear()))
						.collect(Collectors.toList());
	}
	
	public static List<MidiaDTO> transformaEmListaMidiasDTO(List<Midia> medias) {
		return medias.stream()
				.map(media -> 
					new MidiaDTO(media.getTitle(), media.getYear(), media.getImdbId(), media.getCategoria().getDescricao(), media.getFoto()))
						.collect(Collectors.toList());
	}

}
