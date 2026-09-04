package com.musicale.aldrighi.escala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dia_comercial")
public class DiaComercial {

	public DiaComercial(Long id) {
		this.id = id;
	}

	@Id
	private Long id;

	private String descricao;

	private Boolean ativo;

}
