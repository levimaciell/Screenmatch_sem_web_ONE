package dev.levimaciell.Screenmatch_v2;

import dev.levimaciell.Screenmatch_v2.model.DadosSerie;
import dev.levimaciell.Screenmatch_v2.service.ConsumoAPI;
import dev.levimaciell.Screenmatch_v2.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchV2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchV2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ConsumoAPI api = new ConsumoAPI();
		var json = api.obterDados("http://www.omdbapi.com/?apikey=7d3b67c3&t=prison+break");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		System.out.println("Hello world!");
	}
}
