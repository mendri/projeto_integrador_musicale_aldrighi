package com.musicale.aldrighi.escala.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicale.aldrighi.escala.model.Escala;
import com.musicale.aldrighi.escala.service.EscalaService;

@RestController
@RequestMapping("/api/escala")
public class EscalaController {

	private final EscalaService escalaService;

	public EscalaController(EscalaService escalaService) {
		this.escalaService = escalaService;
	}

	@PostMapping("")
	public ResponseEntity<Escala> adicionarEscala(@RequestBody Escala escala) {
		Escala escalaSalva = escalaService.salvar(escala);
		return ResponseEntity.created(null).body(escalaSalva);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerEscala(@PathVariable Long id) {
		escalaService.remover(id);
		return ResponseEntity.noContent().build();
	}

}
