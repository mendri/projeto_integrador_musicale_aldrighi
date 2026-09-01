package com.musicale.aldrighi.escala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dia_comercial")
public class DiaComercial {

	@Id
	private Long id;

	private String descricao;

	private Boolean ativo;

}
