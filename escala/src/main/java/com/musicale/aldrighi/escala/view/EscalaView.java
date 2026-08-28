package com.musicale.aldrighi.escala.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.service.HorarioComercialService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/escala")
public class EscalaView {

	private final HorarioComercialService horarioComercialService;

	public EscalaView(HorarioComercialService horarioComercialService) {
		this.horarioComercialService = horarioComercialService;
	}

	@GetMapping("")
	public String escala(ModelMap model) {
		List<DiaComercial> diasComerciais = horarioComercialService.buscarTodosDiasComerciais();
		Map<Long, List<HorarioComercial>> escalaMap = new HashMap<>();

		for (DiaComercial dia : diasComerciais) {
			List<HorarioComercial> horarios = horarioComercialService.buscarPorDiaComercial(dia.getId());
			escalaMap.put(dia.getId(), horarios);
		}

		model.addAttribute("escala", escalaMap);

		return "escala";
	}
	
}
