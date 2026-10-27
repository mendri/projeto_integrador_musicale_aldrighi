
package com.musicale.aldrighi.escala.controller;

import static org.mockito.ArgumentMatchers.argThat;
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

import com.musicale.aldrighi.escala.api.HorarioComercialController;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.service.HorarioComercialService;

@WebMvcTest(HorarioComercialController.class)
class HorarioComercialControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private HorarioComercialService horarioService;

	@Test
	void deveCriarHorario() throws Exception {
		when(horarioService.validarHorario(any(HorarioComercial.class)))
				.thenReturn(null);

		mockMvc.perform(post("/api/horario-comercial/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "horaInicio": "09:00",
						    "horaFim": "10:00",
						    "diaComercialId": 1
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message")
						.value("Horário criado com sucesso"))
				.andExpect(jsonPath("$.horario.horaInicio")
						.value("09:00"))
				.andExpect(jsonPath("$.horario.horaFim")
						.value("10:00"));

		verify(horarioService).validarHorario(
				argThat(h -> "09:00".equals(h.getHoraInicio())
						&& "10:00".equals(h.getHoraFim())
						&& h.getDiaComercial().getId().equals(1L)));

		verify(horarioService).salvar(any(HorarioComercial.class));
	}

	@Test
	void deveRetornarErroQuandoHorarioForInvalido()
			throws Exception {

		when(horarioService.validarHorario(any(HorarioComercial.class)))
				.thenReturn("Horário inválido. O formato deve ser HH:mm.");

		mockMvc.perform(post("/api/horario-comercial/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						    "horaInicio": "25:00",
						    "horaFim": "10:00",
						    "diaComercialId": 1
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message")
						.value("Horário inválido. O formato deve ser HH:mm."))
				.andExpect(jsonPath("$.horario").value(org.hamcrest.Matchers.nullValue()));

		verify(horarioService).validarHorario(any(HorarioComercial.class));
		verify(horarioService, never()).salvar(any(HorarioComercial.class));
	}

	@Test
	void deveDeletarHorario() throws Exception {
		mockMvc.perform(delete("/api/horario-comercial/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message")
						.value("Horário deletado com sucesso"));

		verify(horarioService).deletar(1L);
	}

	@Test
	void deveRetornar404QuandoHorarioNaoExistir()
			throws Exception {

		doThrow(new RuntimeException(
				"Horário comercial não encontrado"))
				.when(horarioService).deletar(99L);

		mockMvc.perform(delete("/api/horario-comercial/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message")
						.value("Horário comercial não encontrado"));

		verify(horarioService).deletar(99L);
	}
}
