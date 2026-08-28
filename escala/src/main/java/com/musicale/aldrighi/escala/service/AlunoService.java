package com.musicale.aldrighi.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.model.Aluno;
import com.musicale.aldrighi.escala.repository.IAluno;

@Service
public class AlunoService {

	private final IAluno repository;

	public AlunoService(IAluno repository) {
		this.repository = repository;
	}

	public List<Aluno> listarTodos() {
		return repository.findAll();
	}

	public void salvar(Aluno aluno) {
		repository.save(aluno);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}

}
