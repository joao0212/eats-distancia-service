package br.com.caelum.eats.distancia;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends MongoRepository<Restaurante, ObjectId> {

	Page<Restaurante> findAllByAprovadoAndTipoDeCozinhaId(Boolean aprovado, Long tipoDeCozinhaId, Pageable limit);

	Optional<Restaurante> findById(ObjectId id);

	Page<Restaurante> findAllByAprovado(Boolean aprovado, Pageable limit);

	Restaurante findByIdRestauranteAndCep(Long idRestaurante, String cep);

}
