package com.musicale.aldrighi.escala.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicale.aldrighi.escala.dto.AulaDTO;
import com.musicale.aldrighi.escala.model.Aula;
import com.musicale.aldrighi.escala.service.AulaService;

@RestController
@RequestMapping("/api/aula")
public class AulaController {

	private final AulaService service;

	public AulaController(AulaService service) {
		this.service = service;
	}

	@PostMapping("")
	public ResponseEntity<Object> criar(@RequestBody AulaDTO dto) {
		Aula aula = new Aula();
		aula.setDescricao(dto.descricao());

		service.salvar(aula);

		return ResponseEntity.status(201).body("Aula criada com sucesso");

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(200).body("Aula deletada com sucesso");
	}

}
