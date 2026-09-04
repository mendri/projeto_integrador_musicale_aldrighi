package com.musicale.aldrighi.escala.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicale.aldrighi.escala.dto.HorarioComercialDTO;
import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.service.HorarioComercialService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/horario-comercial")
public class HorarioComercialController {

	private final HorarioComercialService horarioService;

	public HorarioComercialController(HorarioComercialService horarioService) {
		this.horarioService = horarioService;
	}

	@PostMapping("/{id}")
	public ResponseEntity<Object> criarHorario(@RequestBody HorarioComercialDTO dto) {
		Map<String, Object> response = new HashMap<>();

		HorarioComercial horarioComercial = new HorarioComercial();
		horarioComercial.setHoraInicio(dto.horaInicio());
		horarioComercial.setHoraFim(dto.horaFim());
		horarioComercial.setDiaComercial(new DiaComercial(dto.diaComercialId()));

		String horarioValido = horarioService.validarHorario(horarioComercial);
		
		if (horarioValido == null) {
			horarioService.salvar(horarioComercial);
	
			response.put("message", "Horário criado com sucesso");
			response.put("horario", horarioComercial);
			return ResponseEntity.status(201).body(response);
		}

		response.put("message", horarioValido);
		response.put("horario", null);

		return ResponseEntity.status(400).body(response);
	}
}
