package br.com.caelum.eats.distancia;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("restaurante")
public class Restaurante {

	public Restaurante() {

	}

	public Restaurante(@NotBlank @Size(max = 9) String cep, Boolean aprovado, Long tipoDeCozinhaId,
			Long idRestaurante) {
		super();
		this.cep = cep;
		this.aprovado = aprovado;
		this.tipoDeCozinhaId = tipoDeCozinhaId;
		this.idRestaurante = idRestaurante;
	}

	public Restaurante(ObjectId id, @NotBlank @Size(max = 9) String cep, Boolean aprovado, Long tipoDeCozinhaId) {
		super();
		this.id = id;
		this.cep = cep;
		this.aprovado = aprovado;
		this.tipoDeCozinhaId = tipoDeCozinhaId;
	}

	@Id
	private ObjectId id;

	private Long idRestaurante;

	@NotBlank
	@Size(max = 9)
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

	public Long getTipoDeCozinhaId() {
		return tipoDeCozinhaId;
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

	public void setTipoDeCozinhaId(Long tipoDeCozinhaId) {
		this.tipoDeCozinhaId = tipoDeCozinhaId;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurante other = (Restaurante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
