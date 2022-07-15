package com.movie.battle.moviebattle.classes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonRead {
	private String endereco;
	private JSONParser parser;
	
	public JsonRead() {
		this.parser = new JSONParser();
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public JSONObject lerArquivoJson(String arquivo) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/" + arquivo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
