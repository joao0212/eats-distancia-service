package br.com.caelum.eats.distancia.base;

import br.com.caelum.eats.distancia.Restaurante;
import br.com.caelum.eats.distancia.RestauranteController;
import br.com.caelum.eats.distancia.RestauranteRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@ImportAutoConfiguration(exclude= MongoAutoConfiguration.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestaurantesBase {

    @Autowired
    private RestauranteController restaurantesController;

    @MockBean
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void before() {
        RestAssuredMockMvc.standaloneSetup(restaurantesController);

        Mockito.when(restauranteRepository.insert(Mockito.any(Restaurante.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    Restaurante restaurante = invocation.getArgument(0);
                    return restaurante;
                });

    }

}
