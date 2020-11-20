package br.com.caelum.eats.distancia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/*
 * Serviço que simula a obtenção dos restaurantes mais próximos a um dado CEP.
 * Deve evoluir para uma solução que utiliza geolocalização.
 * 
 */
@Service
public class DistanciaService {

	private static final Pageable LIMIT = PageRequest.of(0, 5);

	private RestauranteRepository restauranteRepository;

	private RecomendacaoGRpcService recomendacaoGRpcService;

	public DistanciaService(RestauranteRepository restauranteRepository,
			RecomendacaoGRpcService recomendacaoGRpcService) {
		this.restauranteRepository = restauranteRepository;
		this.recomendacaoGRpcService = recomendacaoGRpcService;
	}

	public List<RestauranteComDistanciaDto> listarRestaurantesMaisProximosAoCep(String cep) {
		List<Restaurante> aprovados = restauranteRepository.findAllByAprovado(true, LIMIT).getContent();
		List<RestauranteDto> aprovadosDTO = aprovados.stream().map(aprovado -> this.transformarParaDTO(aprovado))
				.collect(Collectors.toList());
		return calcularDistanciaParaOsRestaurantes(aprovadosDTO, cep);
	}

	public List<RestauranteComDistanciaDto> listarRestaurantesDoTipoDeCozinhaMaisProximosAoCep(Long tipoDeCozinhaId,
			String cep) {
		List<Restaurante> aprovadosDoTipoDeCozinha = restauranteRepository
				.findAllByAprovadoAndTipoDeCozinhaId(true, tipoDeCozinhaId, LIMIT).getContent();
		List<RestauranteDto> aprovadosDoTipoDeCozinhaDTO = aprovadosDoTipoDeCozinha.stream()
				.map(aprovado -> this.transformarParaDTO(aprovado)).collect(Collectors.toList());
		return calcularDistanciaParaOsRestaurantes(aprovadosDoTipoDeCozinhaDTO, cep);
	}

	public RestauranteComDistanciaDto buscarRestauranteComDistanciaDoCep(ObjectId restauranteId, String cep) {
		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new ResourceNotFoundException());
		Long idRestaurante = restaurante.getIdRestaurante();
		BigDecimal distancia = verificarDistanciaDoCep();
		return new RestauranteComDistanciaDto(distancia, idRestaurante);
	}

	public RestauranteComDistanciaDto buscarRestauranteComDistanciaDoCep(Long restauranteId, String cep) {
		Restaurante restaurante = restauranteRepository.findByIdRestauranteAndCep(restauranteId, cep);
		BigDecimal distancia = verificarDistanciaDoCep();
		return new RestauranteComDistanciaDto(distancia, restaurante.getIdRestaurante());
	}

	private List<RestauranteComDistanciaDto> calcularDistanciaParaOsRestaurantes(List<RestauranteDto> restaurantes,
			String cep) {
		return this.ordernarPorRecomendacao(restaurantes).stream().map(restaurante -> {
			BigDecimal distancia = verificarDistanciaDoCep();
			Long idRestaurante = restaurante.getIdRestaurante();
			return new RestauranteComDistanciaDto(distancia, idRestaurante);
		}).collect(Collectors.toList());
	}

	private BigDecimal verificarDistanciaDoCep() {
		return calcularDistancia();
	}

	private BigDecimal calcularDistancia() {
		this.simularDemora();
		return new BigDecimal(Math.random() * 15);
	}

	private void simularDemora() {
		long demora = (long) (Math.random() * 10000 + 10000);
		try {
			Thread.sleep(demora);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private List<RestauranteDto> ordernarPorRecomendacao(List<RestauranteDto> restaurantes) {
		if (restaurantes.size() > 1) {
			List<Long> idsDeRestaurantes = restaurantes.stream().map(RestauranteDto::getIdRestaurante)
					.collect(Collectors.toList());

			List<Long> idsDeRestaurantesOrdenadosPorRecomendacao = recomendacaoGRpcService
					.ordernarPorRecomendacoes(idsDeRestaurantes);

			List<RestauranteDto> restaurantesOrdenadosPorRecomendacao = new ArrayList<>(restaurantes);
			restaurantesOrdenadosPorRecomendacao.sort(Comparator.comparing(
					restaurante -> idsDeRestaurantesOrdenadosPorRecomendacao.indexOf(restaurante.getIdRestaurante())));
			return restaurantesOrdenadosPorRecomendacao;
		}
		return restaurantes;
	}

	private RestauranteDto transformarParaDTO(Restaurante restaurante) {
		return new RestauranteDto(restaurante.getId(), restaurante.getCep(), restaurante.getAprovado(),
				restaurante.getTipoDeCozinhaId(), restaurante.getIdRestaurante());
	}
}
