package br.com.caelum.eats.distancia;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import br.com.caelum.eats.recomendacoes.grpc.Recomendacoes.Restaurantes;
//import br.com.caelum.eats.recomendacoes.grpc.RecomendacoesDeRestaurantesGrpc;
//import br.com.caelum.eats.recomendacoes.grpc.RecomendacoesDeRestaurantesGrpc.RecomendacoesDeRestaurantesBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class RecomendacaoGRpcService {

	private String recomendacaoServiceHost;
	private Integer recomendacaoServicePort;
	private ManagedChannel channel;
	//private RecomendacoesDeRestaurantesBlockingStub recomendacao;

	public RecomendacaoGRpcService(@Value("${recomendacoes.service.host}") String recomendacaoServiceHost,
			@Value("${recomendacoes.service.port}") Integer recomendacaoServicePort) {
		this.recomendacaoServiceHost = recomendacaoServiceHost;
		this.recomendacaoServicePort = recomendacaoServicePort;
	}

	@PostConstruct
	void conectarAoServidorGRpcRecomendacaoService() {
		channel = ManagedChannelBuilder.forAddress(this.recomendacaoServiceHost, this.recomendacaoServicePort)
				.usePlaintext().build();

		//recomendacao = RecomendacoesDeRestaurantesGrpc.newBlockingStub(channel);
	}

	public List<Long> ordernarPorRecomendacoes(List<Long> idsRestaurante) {
		//Restaurantes restaurantes = Restaurantes.newBuilder().addAllRestauranteId(idsRestaurante).build();
		//Restaurantes restaurantesOrdenadosPorRecomendacao = recomendacao.recomendacoes(restaurantes);
		//List<Long> restaurantesOrdenados = restaurantesOrdenadosPorRecomendacao.getRestauranteIdList();

		return null;//restaurantesOrdenados;
	}

	@PreDestroy
	void desconectarDoServidorGRpcRecomendacaoService() {
		channel.shutdown();
	}

}
