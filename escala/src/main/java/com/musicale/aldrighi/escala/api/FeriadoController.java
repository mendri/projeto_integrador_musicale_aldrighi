package com.musicale.aldrighi.escala.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicale.aldrighi.escala.dto.AlertaFeriadoDTO;
import com.musicale.aldrighi.escala.service.AlertaFeriadoService;

@RestController
@RequestMapping("/api/feriados")
public class FeriadoController {

	private final AlertaFeriadoService service;

	public FeriadoController(AlertaFeriadoService service) {
		this.service = service;
	}

	@GetMapping("/proximos")
	public List<AlertaFeriadoDTO> proximos() {
		return service.buscarAlertas();
	}

}
