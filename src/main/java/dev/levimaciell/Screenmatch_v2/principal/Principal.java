package dev.levimaciell.Screenmatch_v2.principal;

import dev.levimaciell.Screenmatch_v2.model.DadosEpisodio;
import dev.levimaciell.Screenmatch_v2.model.DadosSerie;
import dev.levimaciell.Screenmatch_v2.model.DadosTemporada;
import dev.levimaciell.Screenmatch_v2.model.Episodio;
import dev.levimaciell.Screenmatch_v2.service.ConsumoAPI;
import dev.levimaciell.Screenmatch_v2.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7d3b67c3&t";
    private ConsumoAPI api = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca:");
        var nomeSerie = sc.nextLine();
        var url = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;

        var json = api.obterDados(url);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for(int i = 1; i <= dados.totalTemporadas(); i++){
            url = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY + "&Season=" + i;
            json = api.obterDados(url);

			DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(temporada);
			System.out.println(temporada);
		}

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodioList = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

//        System.out.println("Melhores 10 episódios:");
//        dadosEpisodioList.stream()
//                .filter(f -> !f.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(p -> System.out.println("Primeiro filtro N/A" + p))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(p -> System.out.println("Ordenação " + p))
//                .limit(10)
//                .peek(p -> System.out.println("limitando em 10" + p))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(p -> System.out.println("map para to upper case" + p))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Digite o nome do episodio: ");
//        var trechoTitulo = sc.nextLine();
//
//        Optional<Episodio> ep = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
////        ep.ifPresent(System.out::println);
//        if(ep.isPresent()){
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada: " + ep.get().getTemporada());
//        }
//        else {
//            System.out.println("Episodio não encontrado");
//        }

//
//        System.out.println("A partir de que ano você deseja ver os episodios?");
//        var ano = Integer.parseInt(sc.nextLine());
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "Episodio: " + e.getTitulo() +
//                                "Data lancamento: " + e.getDataLancamento().format(dtf)
//                ));


        Map<Integer, Double> avaliacoesTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesTemporada);

        DoubleSummaryStatistics stats = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println(stats);

    }

}
