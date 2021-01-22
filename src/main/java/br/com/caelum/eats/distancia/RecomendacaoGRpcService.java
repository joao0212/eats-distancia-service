package br.com.caelum.eats.distancia;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import br.com.caelum.eats.recomendacoes.grpc.Recomendacoes;
import br.com.caelum.eats.recomendacoes.grpc.RecomendacoesDeRestaurantesGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class RecomendacaoGRpcService {

	private String recomendacaoServiceHost;
	private Integer recomendacaoServicePort;
	private ManagedChannel channel;
	private RecomendacoesDeRestaurantesGrpc.RecomendacoesDeRestaurantesBlockingStub recomendacao;

	public RecomendacaoGRpcService(@Value("${recomendacoes.service.host}") String recomendacaoServiceHost,
			@Value("${recomendacoes.service.port}") Integer recomendacaoServicePort) {
		this.recomendacaoServiceHost = recomendacaoServiceHost;
		this.recomendacaoServicePort = recomendacaoServicePort;
	}

	@PostConstruct
	void conectarAoServidorGRpcRecomendacaoService() {
		channel = ManagedChannelBuilder.forAddress(this.recomendacaoServiceHost, this.recomendacaoServicePort)
				.usePlaintext().build();

		recomendacao = RecomendacoesDeRestaurantesGrpc.newBlockingStub(channel);
	}

	public List<Long> ordernarPorRecomendacoes(List<Long> idsRestaurante) {
		Recomendacoes.Restaurantes restaurantes = Recomendacoes.Restaurantes.newBuilder().addAllRestauranteId(idsRestaurante).build();
		Recomendacoes.Restaurantes restaurantesOrdenadosPorRecomendacao = recomendacao.recomendacoes(restaurantes);
		List<Long> restaurantesOrdenados = restaurantesOrdenadosPorRecomendacao.getRestauranteIdList();

		return restaurantesOrdenados;
	}

	@PreDestroy
	void desconectarDoServidorGRpcRecomendacaoService() {
		channel.shutdown();
	}

}
