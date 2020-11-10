package br.com.caelum.eats.distancia;

import java.net.URI;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	private RestauranteRepository restauranteRepository;

	public RestauranteController(RestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}

	@PostMapping
	ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante,
			UriComponentsBuilder uriComponentsBuilder) {
		Restaurante restauranteSalvo = restauranteRepository.save(new Restaurante(restaurante.getCep(),
				restaurante.getAprovado(), restaurante.getTipoDeCozinhaId(), restaurante.getIdRestaurante()));
		URI uri = uriComponentsBuilder.path("/restaurantes/{id}").buildAndExpand(restauranteSalvo.getId()).toUri();
		return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(restauranteSalvo);
	}

	@PutMapping("/{id}")
	Restaurante atualizar(@PathVariable("id") ObjectId id, @RequestBody Restaurante restaurante) {
		Restaurante restauranteSalvo = restauranteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException());
		restauranteSalvo.setId(restaurante.getId());
		restauranteSalvo.setAprovado(restaurante.getAprovado());
		restauranteSalvo.setCep(restaurante.getCep());
		restauranteSalvo.setTipoDeCozinhaId(restaurante.getTipoDeCozinhaId());
		return restauranteRepository.save(restauranteSalvo);
	}
}
