package com.musicale.aldrighi.escala.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.musicale.aldrighi.escala.api.DiaComercialController;
import com.musicale.aldrighi.escala.service.DiaComercialService;

@WebMvcTest(DiaComercialController.class)
class DiaComercialControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DiaComercialService service;

	@Test
	void deveEditarDiaComercial() throws Exception {
		mockMvc.perform(put("/api/dia-comercial/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "ativo": true
						}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message")
						.value("Dia comercial atualizado com sucesso"));

		verify(service).editar(1L, true);
	}

	@Test
	void deveRetornar404QuandoDiaComercialNaoExistir()
			throws Exception {

		doThrow(new RuntimeException("Dia comercial não encontrado"))
				.when(service).editar(99L, false);

		mockMvc.perform(put("/api/dia-comercial/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "ativo": false
						}
						"""))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message")
						.value("Dia comercial não encontrado"));

		verify(service).editar(99L, false);
	}

	@Test
	void deveEncaminharIdEStatusCorretamente() throws Exception {
		mockMvc.perform(put("/api/dia-comercial/5")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "ativo": false
						}
						"""))
				.andExpect(status().isOk());

		verify(service).editar(5L, false);
	}
}
