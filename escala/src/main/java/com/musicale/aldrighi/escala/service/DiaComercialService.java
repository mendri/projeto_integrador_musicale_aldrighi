package com.musicale.aldrighi.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.repository.IDiaComercial;

@Service
public class DiaComercialService {

	private final IDiaComercial repository;

	public DiaComercialService(IDiaComercial repository) {
		this.repository = repository;
	}

	public List<DiaComercial> buscarTodos() {
		return repository.findAll();
	}

	public void editar(Long id, Boolean ativo) {
		DiaComercial diaComercial = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Dia comercial não encontrado"));
		diaComercial.setAtivo(ativo);

		repository.save(diaComercial);
	}

}
