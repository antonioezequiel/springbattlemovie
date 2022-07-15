package com.movie.battle.moviebattle.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.movie.battle.moviebattle.DTO.MovieDTO;
import com.movie.battle.moviebattle.DTO.SerieDTO;
import com.movie.battle.moviebattle.classes.JsonRead;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.classes.Serie;
import com.movie.battle.moviebattle.repository.SerieRepository;

@Service
public class SerieService {

	SerieRepository serieRepository;
	PartidaService partidaService;

	public SerieService() throws IOException {
	}

	@Autowired
	public SerieService(SerieRepository serieRepository, @Lazy PartidaService partidaService) throws IOException {
		super();
		this.serieRepository = serieRepository;
		this.partidaService = partidaService;
	}

	@SuppressWarnings("unchecked")
	public void carregarFilmesIMDB() {
		JsonRead jsonRead = new JsonRead();
		JSONObject jObj = jsonRead.lerArquivoJson("repositorioIMDBSerie.json");
		JSONArray arr = (JSONArray) jObj.get("paginas");

		arr.stream().forEach(pg -> {
			final JSONObject p = (JSONObject) pg;
			final Document documento = lerPaginaIMDB((String) p.get("pagina"));
			List<Serie> series = montaObjetosSerie(documento);
			saveSeriesPagina(series);
		});

	}

	private List<Serie> montaObjetosSerie(Document documento) {
		Elements body = documento.select("div.lister-list");
		List<Serie> series = new ArrayList<Serie>();
		for (Element row : body.select("div.lister-item-content")) {
			final String imdbId = row.select("h3.lister-item-header span").get(0).text().replaceAll("[\\.]", "");
			final String title = row.select("h3.lister-item-header a").text();
			final String year = row.select("h3.lister-item-header span").get(1).text().replaceAll("[^\\d]", "");
			final Double rating = Double.parseDouble(row.select("div.ratings-bar strong").text());
			final String votes = row.select("p.sort-num_votes-visible span").get(1).text().replaceAll(",", "");

			Serie serie = new Serie(imdbId, title, year, rating, votes);
			var score = serie.getRating() * Double.parseDouble(serie.getVotes().replace(",", ""));
			serie.setScore(score);
			series.add(serie);
		}
		for (int i = 0; i < body.select("div.lister-item-image").size(); i++) {
			Element row = body.select("div.lister-item-image").get(i);
			final String foto = row.select("a img[src]").get(0).attr("loadlate");
			series.get(i).setFoto(foto);
		}

		return series;
	}

	private Document lerPaginaIMDB(String pagina) {
		Document document = null;
		try {
			document = Jsoup.connect(pagina).timeout(6000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	private void saveSeriesPagina(List<Serie> series) {
		series.forEach(serie -> serieRepository.save(serie));
	}

	public Serie findById(String imdbID) {
		return serieRepository.findById(imdbID).get();
	}

	public List<SerieDTO> sortearFilmes(Partida partida) {
		List<Serie> series = serieRepository.findAll();
		List<SerieDTO> seriesDTO = new ArrayList<SerieDTO>();
		Random r = new Random();
		int index1, index2;

		do {
			index1 = r.nextInt(series.size() - 1);
			index2 = r.nextInt(series.size() - 1);
		} while (index1 == index2);

		SerieDTO serieDTO = SerieDTO.transformaEmDTO(series.get(index1));
		seriesDTO.add(serieDTO);
		serieDTO = SerieDTO.transformaEmDTO(series.get(index2));
		seriesDTO.add(serieDTO);

		partida.setSeries(SerieDTO.transformaEmListaMovies(seriesDTO));

		atualizarSeriesSorteadosPartida(partida);

		return seriesDTO;
	}

	private void atualizarSeriesSorteadosPartida(Partida partida) {
		partidaService.atualizarPartida(partida);
	}
}
