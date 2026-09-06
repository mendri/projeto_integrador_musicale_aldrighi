package com.musicale.aldrighi.escala.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicale.aldrighi.escala.dto.DiaComercialDTO;
import com.musicale.aldrighi.escala.service.DiaComercialService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/dia-comercial")
public class DiaComercialController {

	private final DiaComercialService service;

	public DiaComercialController(DiaComercialService diaComercialService) {
		this.service = diaComercialService;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> editar(@RequestBody DiaComercialDTO dto, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			service.editar(id, dto.ativo());
			response.put("message", "Dia comercial atualizado com sucesso");
		} catch (Exception e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(404).body(response);
		}
		
		return ResponseEntity.status(200).body(response);
	}

}
