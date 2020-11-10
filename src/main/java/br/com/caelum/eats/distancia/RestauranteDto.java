package br.com.caelum.eats.distancia;

import org.bson.types.ObjectId;

public class RestauranteDto {

	public RestauranteDto(ObjectId id, String cep, Boolean aprovado, Long tipoDeCozinhaId, Long idRestaurante) {
		super();
		this.id = id;
		this.cep = cep;
		this.aprovado = aprovado;
		this.tipoDeCozinhaId = tipoDeCozinhaId;
		this.idRestaurante = idRestaurante;
	}

	private ObjectId id;
	private Long idRestaurante;
	private String cep;
	private Boolean aprovado;
	private Long tipoDeCozinhaId;

	public ObjectId getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Long getTipoDeCozinhaId() {
		return tipoDeCozinhaId;
	}

	public void setTipoDeCozinhaId(Long tipoDeCozinhaId) {
		this.tipoDeCozinhaId = tipoDeCozinhaId;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}
}
