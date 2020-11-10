package br.com.caelum.eats.distancia;

import java.math.BigDecimal;

public class RestauranteComDistanciaDto {

	public RestauranteComDistanciaDto() {

	}

	public RestauranteComDistanciaDto(BigDecimal distancia, Long idRestaurante) {
		super();
		this.distancia = distancia;
		this.idRestaurante = idRestaurante;
	}

	private BigDecimal distancia;
	private Long idRestaurante;

	public BigDecimal getDistancia() {
		return distancia;
	}

	public void setDistancia(BigDecimal distancia) {
		this.distancia = distancia;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}
}
