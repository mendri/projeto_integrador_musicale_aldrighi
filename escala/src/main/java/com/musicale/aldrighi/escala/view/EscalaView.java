package com.musicale.aldrighi.escala.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.Escala;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.service.AlunoService;
import com.musicale.aldrighi.escala.service.AulaService;
import com.musicale.aldrighi.escala.service.EscalaService;
import com.musicale.aldrighi.escala.service.HorarioComercialService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/escala")
public class EscalaView {

	private final HorarioComercialService horarioComercialService;

	private final AlunoService alunoService;

	private final AulaService aulaService;

	private final EscalaService escalaService;

	public EscalaView(HorarioComercialService horarioComercialService, AlunoService alunoService, AulaService aulaService, EscalaService escalaService) {
		this.horarioComercialService = horarioComercialService;
		this.alunoService = alunoService;
		this.aulaService = aulaService;
		this.escalaService = escalaService;
	}

	@GetMapping("")
	public String escala(ModelMap model) {
		List<DiaComercial> diasComerciais = horarioComercialService.buscarTodosDiasComerciaisAtivos();
		Map<Long, List<Escala>> escalaMap = new HashMap<>();
		Map<Long, List<HorarioComercial>> horariosDisponiveisMap = new HashMap<>();

		for (DiaComercial dia : diasComerciais) {
			escalaMap.put(dia.getId(), escalaService.buscarPorDiaComercial(dia.getId()));
			horariosDisponiveisMap.put(dia.getId(), horarioComercialService.buscarPorDiaComercial(dia.getId()));
		}

		model.addAttribute("escala", escalaMap);
		model.addAttribute("horariosDisponiveis", horariosDisponiveisMap);
		model.addAttribute("alunos", alunoService.buscarTodos());
		model.addAttribute("aulas", aulaService.buscarTodos());

		return "escala";
	}
	
}
