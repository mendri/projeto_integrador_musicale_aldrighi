package com.musicale.aldrighi.escala.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.musicale.aldrighi.escala.api.FeriadoController;
import com.musicale.aldrighi.escala.dto.AlertaFeriadoDTO;
import com.musicale.aldrighi.escala.service.AlertaFeriadoService;

@WebMvcTest(FeriadoController.class)
class FeriadoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AlertaFeriadoService alertaFeriadoService;

	@Test
	void deveListarAlertasDeFeriadosProximos() throws Exception {
		when(alertaFeriadoService.buscarAlertas())
				.thenReturn(List.of(new AlertaFeriadoDTO("20/11/2026", "Dia da Consciência Negra", "Sexta-feira", 3)));

		mockMvc.perform(get("/api/feriados/proximos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value("Dia da Consciência Negra"))
				.andExpect(jsonPath("$[0].diaComercial").value("Sexta-feira"))
				.andExpect(jsonPath("$[0].totalAulas").value(3));
	}

}
