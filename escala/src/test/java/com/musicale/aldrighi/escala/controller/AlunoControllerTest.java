package com.musicale.aldrighi.escala.controller;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.musicale.aldrighi.escala.api.AlunoController;
import com.musicale.aldrighi.escala.model.Aluno;
import com.musicale.aldrighi.escala.service.AlunoService;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AlunoService service;

	@Test
	void deveCriarAluno() throws Exception {
		mockMvc.perform(post("/api/aluno")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "nome": "João Silva"
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(content().string(
						"Aluno criado com sucesso"));

		verify(service).salvar(argThat(
				(Aluno aluno) -> "João Silva".equals(aluno.getNome())));
	}

	@Test
	void deveDeletarAluno() throws Exception {
		mockMvc.perform(delete("/api/aluno/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(
						"Aluno deletado com sucesso"));

		verify(service).deletar(1L);
	}
}