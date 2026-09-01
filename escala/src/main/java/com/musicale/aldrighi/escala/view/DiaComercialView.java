package com.musicale.aldrighi.escala.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.service.DiaComercialService;
import com.musicale.aldrighi.escala.service.HorarioComercialService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/dia")
public class DiaComercialView {

	private final DiaComercialService service;

	private final HorarioComercialService horarioService;

	public DiaComercialView(DiaComercialService service, HorarioComercialService horarioService) {
		this.service = service;
		this.horarioService = horarioService;
	}

	@GetMapping("")
	public String listar(ModelMap model) {
		List<DiaComercial> diasComerciais = service.buscarTodos();
		Map<Long, List<HorarioComercial>> diasHorarios = new HashMap<>();
		Map<Long, DiaComercial> diasDescricao = new HashMap<>();

		for (DiaComercial diaComercial : diasComerciais) {
			diasDescricao.put(diaComercial.getId(), diaComercial);

			if (!diaComercial.getAtivo()) {
				diasHorarios.put(diaComercial.getId(), List.of());
				continue;	
			}

			List<HorarioComercial> horarios = horarioService.buscarPorDiaComercial(diaComercial.getId());
			diasHorarios.put(diaComercial.getId(), horarios);
		}

		model.addAttribute("diasHorarios", diasHorarios);
		model.addAttribute("diasComerciais", diasDescricao);

		return "dia/listar";
	}
	
}
