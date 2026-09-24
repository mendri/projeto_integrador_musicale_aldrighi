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

	public Escala salvar(Escala escala) {
		Escala escalaSalva = repository.save(escala);
		return repository.findById(escalaSalva.getId())
				.orElseThrow(() -> new RuntimeException("Erro ao salvar a escala"));
	}

	public void remover(Long id) {
		repository.deleteById(id);
	}

}
