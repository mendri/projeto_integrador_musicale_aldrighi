
package com.musicale.aldrighi.escala.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.musicale.aldrighi.escala.api.EscalaController;
import com.musicale.aldrighi.escala.model.Escala;
import com.musicale.aldrighi.escala.service.EscalaService;

@WebMvcTest(EscalaController.class)
class EscalaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EscalaService escalaService;

	@Test
	void deveAdicionarEscala() throws Exception {
		Escala escalaSalva = new Escala();
		escalaSalva.setId(1L);

		when(escalaService.salvar(any(Escala.class)))
				.thenReturn(escalaSalva);

		mockMvc.perform(post("/api/escala")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "id": null
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1));

		verify(escalaService).salvar(any(Escala.class));
	}

	@Test
	void deveRemoverEscala() throws Exception {
		mockMvc.perform(delete("/api/escala/1"))
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));

		verify(escalaService).remover(1L);
	}
}
