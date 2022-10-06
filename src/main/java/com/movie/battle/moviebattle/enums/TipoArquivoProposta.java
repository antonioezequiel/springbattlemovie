package com.movie.battle.moviebattle.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoArquivoProposta {
	
	DOCUMENTO_CHEFIA_IMEDIATA(1, "Documento Chefia Imediata"),
	CRONOGRAMA(2, "Cronograma"),
	ANEXO(3, "Anexo"),
	APENDICE(4, "Apêndice"),
	OUTROSOS(5, "Outros");

	private int cod;
	private String descricao;

	private TipoArquivoProposta(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}

	public String getDescricao () {
		return descricao;
	}
	
	/**
	 * @param cod the cod to set
	 */
	public void setCod(int cod) {
		this.cod = cod;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@JsonCreator
	public static TipoArquivoProposta toEnum(Integer cod) {

		
		for (TipoArquivoProposta x : TipoArquivoProposta.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		return null;
	}
		
	
	public static TipoArquivoProposta toEnum(String estado) {
		
		
		for (TipoArquivoProposta e : TipoArquivoProposta.values()) {
			if (estado.equals(e.getDescricao())) {
				return e;
			}
		}
		return null;
		
	
	}	
}
