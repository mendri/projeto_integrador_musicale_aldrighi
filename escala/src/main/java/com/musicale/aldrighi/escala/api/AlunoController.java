package com.musicale.aldrighi.escala.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicale.aldrighi.escala.dto.AlunoDTO;
import com.musicale.aldrighi.escala.model.Aluno;
import com.musicale.aldrighi.escala.service.AlunoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/aluno")
public class AlunoController {

	private final AlunoService service;

	public AlunoController(AlunoService service) {
		this.service = service;
	}

	@PostMapping("")
	public ResponseEntity<Object> criar(@RequestBody AlunoDTO dto) {
		Aluno aluno = new Aluno();
		aluno.setNome(dto.nome());

		service.salvar(aluno);
		
		return ResponseEntity.status(201).body("Aluno criado com sucesso");
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(200).body("Aluno deletado com sucesso");
	}

}
