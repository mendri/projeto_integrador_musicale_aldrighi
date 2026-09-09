package com.musicale.aldrighi.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.model.Escala;
import com.musicale.aldrighi.escala.repository.IEscala;

@Service
public class EscalaService {

	private final IEscala repository;

	public EscalaService(IEscala repository) {
		this.repository = repository;
	}

	public List<Escala> buscarTodos() {
		return repository.findAll();
	}

	public List<Escala> buscarPorDiaComercial(Long id) {
		return repository.findByHorarioComercialDiaComercialId(id);
	}

}
