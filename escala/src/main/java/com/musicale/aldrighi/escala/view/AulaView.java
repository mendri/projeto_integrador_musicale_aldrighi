package com.musicale.aldrighi.escala.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicale.aldrighi.escala.model.Aula;
import com.musicale.aldrighi.escala.service.AulaService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/aula")
public class AulaView {

	private final AulaService service;

	public AulaView(AulaService service) {
		this.service = service;
	}

	@GetMapping("")
	public String listar(ModelMap model) {
		List<Aula> aulas = service.buscarTodos();

		model.addAttribute("aulas", aulas);

		return "aula/listar";
	}
	

}
