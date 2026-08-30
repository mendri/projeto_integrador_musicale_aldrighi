package com.musicale.aldrighi.escala.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicale.aldrighi.escala.service.AlunoService;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/aluno")
public class AlunoView {

	private final AlunoService alunoService;

	public AlunoView(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	@GetMapping("")
	public String listar(ModelMap model) {
		model.addAttribute("alunos", alunoService.buscarTodos());

		return "aluno/listar";
	}

}
