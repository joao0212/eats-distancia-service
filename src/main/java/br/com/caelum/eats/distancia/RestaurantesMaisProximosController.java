package br.com.caelum.eats.distancia;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distancias")
public class RestaurantesMaisProximosController {

	private DistanciaService distanciaService;

	public RestaurantesMaisProximosController(DistanciaService distanciaService) {
		this.distanciaService = distanciaService;
	}

	@GetMapping("/restaurantes/mais-proximos/{cep}")
	List<RestauranteComDistanciaDto> listarMaisProximos(@PathVariable("cep") String cep) {
		return distanciaService.listarRestaurantesMaisProximosAoCep(cep);
	}

	@GetMapping("/restaurantes/mais-proximos/{cep}/tipos-de-cozinha/{tipoDeCozinhaId}")
	List<RestauranteComDistanciaDto> listarMaisProximos(@PathVariable("cep") String cep,
			@PathVariable("tipoDeCozinhaId") Long tipoDeCozinhaId) {
		return distanciaService.listarRestaurantesDoTipoDeCozinhaMaisProximosAoCep(tipoDeCozinhaId, cep);
	}

	@GetMapping("/restaurantes/{cep}/restaurante/{idRestaurante}")
	RestauranteComDistanciaDto buscarComDistanciaPorId(@PathVariable("cep") String cep,
			@PathVariable("idRestaurante") Long restauranteId) {
		return distanciaService.buscarRestauranteComDistanciaDoCep(restauranteId, cep);
	}
}
