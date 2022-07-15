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
import com.movie.battle.moviebattle.classes.JsonRead;
import com.movie.battle.moviebattle.classes.Movie;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.MovieRepository;

@Service
public class MovieService {

	MovieRepository movieRepository;
	PartidaService partidaService;

	public MovieService() throws IOException {
	}

	@Autowired
	public MovieService(MovieRepository movieRepository, @Lazy PartidaService partidaService) throws IOException {
		super();
		this.movieRepository = movieRepository;
		this.partidaService = partidaService;
	}

	@SuppressWarnings("unchecked")
	public void carregarFilmesIMDB() {
		JsonRead jsonRead = new JsonRead();
		JSONObject jObj = jsonRead.lerArquivoJson("repositorioIMDB.json");
		JSONArray arr = (JSONArray) jObj.get("paginas");

		arr.stream().forEach(pg -> {
			final JSONObject p = (JSONObject) pg;
			final Document documento = lerPaginaIMDB((String) p.get("pagina"));
			List<Movie> movies = montaObjetosMovie(documento);
			saveMoviesPagina(movies);
		});

	}

	private List<Movie> montaObjetosMovie(Document documento) {
		Elements body = documento.select("div.lister-list");
		List<Movie> movies = new ArrayList<Movie>();
		for (Element row : body.select("div.lister-item-content")) {
			final String imdbId = row.select("h3.lister-item-header span").get(0).text().replaceAll("[\\.]", "");
			final String title = row.select("h3.lister-item-header a").text();
			final String year = row.select("h3.lister-item-header span").get(1).text().replaceAll("[^\\d]", "");
			final Double rating = Double.parseDouble(row.select("div.ratings-bar strong").text());
			final String votes = row.select("p.sort-num_votes-visible span").get(1).text().replaceAll(",", "");

			Movie movie = new Movie(imdbId, title, year, rating, votes);
			var score = movie.getRating() * Double.parseDouble(movie.getVotes().replace(",", ""));
			movie.setScore(score);
			movies.add(movie);
		}
		for (int i=0; i < body.select("div.lister-item-image").size(); i++) {
			Element row = body.select("div.lister-item-image").get(i);
			final String foto = row.select("a img[src]").get(0).attr("loadlate");
			movies.get(i).setFoto(foto);			
		}
		
		return movies;
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

	private void saveMoviesPagina(List<Movie> movies) {
		movies.forEach(movie -> movieRepository.save(movie));
	}

	public Movie findById(String imdbID) {
		return movieRepository.findById(imdbID).get();
	}

	public List<MovieDTO> sortearFilmes(Partida partida) {
		List<Movie> movies = movieRepository.findAll();
		List<MovieDTO> moviesDTO = new ArrayList<MovieDTO>();
		Random r = new Random();
		int index1, index2;

		do {
			index1 = r.nextInt(movies.size() - 1);
			index2 = r.nextInt(movies.size() - 1);
		} while (index1 == index2);

		MovieDTO moviDTO = MovieDTO.transformaEmDTO(movies.get(index1));
		moviesDTO.add(moviDTO);
		moviDTO = MovieDTO.transformaEmDTO(movies.get(index2));
		moviesDTO.add(moviDTO);

		partida.setMovies(MovieDTO.transformaEmListaMovies(moviesDTO));

		atualizarFilmesSorteadosPartida(partida);

		return moviesDTO;
	}

	private void atualizarFilmesSorteadosPartida(Partida partida) {
		partidaService.atualizarPartida(partida);
	}
}
