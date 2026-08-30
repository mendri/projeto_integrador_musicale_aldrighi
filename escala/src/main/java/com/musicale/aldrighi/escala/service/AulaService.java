package com.musicale.aldrighi.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.model.Aula;
import com.musicale.aldrighi.escala.repository.IAula;

@Service
public class AulaService {

	private final IAula repository;

	public AulaService(IAula repository) {
		this.repository = repository;
	}

	public List<Aula> buscarTodos() {
		return repository.findAll();
	}

	public void salvar(Aula aula) {
		repository.save(aula);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}

}
