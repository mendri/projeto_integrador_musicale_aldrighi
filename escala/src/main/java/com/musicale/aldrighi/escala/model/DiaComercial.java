package com.musicale.aldrighi.escala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dia_comercial")
public class DiaComercial {

	public DiaComercial() {
	}

	public DiaComercial(Long id) {
		this.id = id;
	}

	public DiaComercial(String descricao, Boolean ativo) {
		this.descricao = descricao;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricao;

	private Boolean ativo;

}
