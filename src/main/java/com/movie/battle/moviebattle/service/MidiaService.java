package com.movie.battle.moviebattle.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.movie.battle.moviebattle.DTO.MidiaDTO;
import com.movie.battle.moviebattle.classes.Categoria;
import com.movie.battle.moviebattle.classes.JsonRead;
import com.movie.battle.moviebattle.classes.Midia;
import com.movie.battle.moviebattle.classes.Partida;
import com.movie.battle.moviebattle.repository.MidiaRepository;

@Service
public class MidiaService {

	private final MidiaRepository midiaRepository;
	private final PartidaService partidaService;
	private final CategoriaService categoriaService;
	private final ModelMapper modelMapper;

	@Autowired
	public MidiaService(CategoriaService categoriaService, ModelMapper modelMapper,
			MidiaRepository midiaRepository, @Lazy PartidaService partidaService) throws IOException {
		super();
		this.midiaRepository = midiaRepository;
		this.partidaService = partidaService;
		this.categoriaService = categoriaService;
		this.modelMapper = modelMapper;
		
		/*
		 * Mapeamento para definir apenas a descrição da categoria, como categoria no DTO
		 */
		PropertyMap<Midia, MidiaDTO> midiaMap = new PropertyMap<Midia, MidiaDTO>() {
			  protected void configure() {
			    map().setCategoria(source.getCategoria().getDescricao());
			  }
		};

		modelMapper.addMappings(midiaMap);
	}

	public void carregarMidiasIMDB() {
		JsonRead jsonRead = new JsonRead();		
		
		/*cria as Categorias Padrão*/
		categoriaService.criarCategoriasPadrao();
		
		JSONObject filmes = LerMidaFilmesIMDB(jsonRead);		
		Optional<Categoria> c = categoriaService.findByDescricao("Filme");
		if(c.isPresent())
			lerMidiaPagina(filmes, c.get());
		
		JSONObject series = LerMidaSeriesIMDB(jsonRead);
		Optional<Categoria> c2 = categoriaService.findByDescricao("Serie");
		if(c2.isPresent())
			lerMidiaPagina(series, c2.get());
	}

	@SuppressWarnings("unchecked")
	private void lerMidiaPagina(JSONObject jObj, Categoria categoria) {
		JSONArray arr = (JSONArray) jObj.get("paginas");
		arr.stream().forEach(pg -> {
			final JSONObject p = (JSONObject) pg;
			final Document documento = lerPaginaIMDB((String) p.get("pagina"));
			List<Midia> midias = montaObjetosMidia(documento, categoria);
			saveMidiasPagina(midias);
		});
	}

	private JSONObject LerMidaFilmesIMDB(JsonRead jsonRead) {
		JSONObject jObj = jsonRead.lerArquivoJson("repositorioIMDB-filmes.json");
		return jObj;
	}
	
	private JSONObject LerMidaSeriesIMDB(JsonRead jsonRead) {
		JSONObject jObj = jsonRead.lerArquivoJson("repositorioIMDB-series.json");
		return jObj;
	}

	private List<Midia> montaObjetosMidia(Document documento, Categoria categoria) {
		Elements body = documento.select("div.lister-list");
		List<Midia> midias = new ArrayList<Midia>();
		
		for (Element row : body.select("div.lister-item-content")) {
			final String imdbId = row.select("h3.lister-item-header span").get(0).text().replaceAll("[\\.]", "");
			final String title = row.select("h3.lister-item-header a").text();
			final String year = row.select("h3.lister-item-header span").get(1).text().replaceAll("[^\\d]", "");
			final Double rating = Double.parseDouble(row.select("div.ratings-bar strong").text());
			final String votes = row.select("p.sort-num_votes-visible span").get(1).text().replaceAll(",", "");

			Midia midia = new Midia(imdbId, title, year, rating, votes);
			midia.setCategoria(categoria);
			var score = midia.getRating() * Double.parseDouble(midia.getVotes().replace(",", ""));
			midia.setScore(score);
			midias.add(midia);
		}
		
		for (int i=0; i < body.select("div.lister-item-image").size(); i++) {
			Element row = body.select("div.lister-item-image").get(i);
			final String foto = row.select("a img[src]").get(0).attr("loadlate");
			midias.get(i).setFoto(foto);			
		}
		
		return midias;
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

	private void saveMidiasPagina(List<Midia> midias) {
		midias.forEach(midia -> midiaRepository.save(midia));
	}

	public Midia findById(String imdbID) {
		return midiaRepository.findById(imdbID).get();
	}

	public List<MidiaDTO> sortearMidias(Partida partida) {
		//Se não existir filmes sorteados, o sistema sorteia novos filmes
		if (!partida.isJogadaAtiva()) {
			Categoria categoria = definirCategoriaAleatoria();
			List<Midia> midias = midiaRepository.findByCategoria(categoria);
			
			/* Embaralha a lista de midias*/
			Collections.shuffle(midias);
			
			partida.setMidias(midias.get(0));
			partida.setMidias(midias.get(2));
					
			/* atualiza a jogada no banco de dados*/
			atualizarFilmesSorteadosPartida(partida);
		}
		return transformaListaEmDTO(partida.getMidias());
	}

	private Categoria definirCategoriaAleatoria() {
		return categoriaService.buscarCategoriaAleatoria();
	}
	
	public MidiaDTO transformaEmDTO(Midia midia) {
		return modelMapper.map(midia, MidiaDTO.class);
	}
	
	public List<MidiaDTO> transformaListaEmDTO(List<Midia> midias) {
		return midias.stream().map(midia -> modelMapper.map(midia, MidiaDTO.class))
				.collect(Collectors.toList());
	}

	private void atualizarFilmesSorteadosPartida(Partida partida) {
		partidaService.atualizarPartida(partida);
	}
}
