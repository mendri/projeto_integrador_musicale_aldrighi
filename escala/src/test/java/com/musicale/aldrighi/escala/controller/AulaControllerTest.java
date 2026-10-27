
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

import com.musicale.aldrighi.escala.api.AulaController;
import com.musicale.aldrighi.escala.model.Aula;
import com.musicale.aldrighi.escala.service.AulaService;

@WebMvcTest(AulaController.class)
class AulaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AulaService service;

	@Test
	void deveCriarAula() throws Exception {
		mockMvc.perform(post("/api/aula")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "descricao": "Piano - Individual"
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(content().string(
						"Aula criada com sucesso"));

		verify(service).salvar(argThat(
				(Aula aula) -> "Piano - Individual".equals(aula.getDescricao())));
	}

	@Test
	void deveDeletarAula() throws Exception {
		mockMvc.perform(delete("/api/aula/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(
						"Aula deletada com sucesso"));

		verify(service).deletar(1L);
	}
}
