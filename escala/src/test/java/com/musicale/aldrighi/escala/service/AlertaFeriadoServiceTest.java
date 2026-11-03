package com.musicale.aldrighi.escala.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musicale.aldrighi.escala.dto.AlertaFeriadoDTO;
import com.musicale.aldrighi.escala.dto.FeriadoDTO;
import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.Escala;

@ExtendWith(MockitoExtension.class)
class AlertaFeriadoServiceTest {

	private static final LocalDate HOJE = LocalDate.of(2026, 11, 3);

	@Mock
	private FeriadoService feriadoService;

	@Mock
	private HorarioComercialService horarioComercialService;

	@Mock
	private EscalaService escalaService;

	@InjectMocks
	private AlertaFeriadoService alertaFeriadoService;

	private DiaComercial dia(Long id, String descricao) {
		DiaComercial dia = new DiaComercial(descricao, true);
		dia.setId(id);
		return dia;
	}

	@Test
	void deveAvisarQuandoFeriadoCaiEmDiaComercialComAulas() {
		// 20/11/2026 é uma sexta-feira
		when(feriadoService.buscarProximos(HOJE, AlertaFeriadoService.DIAS_A_FRENTE))
				.thenReturn(List.of(new FeriadoDTO("2026-11-20", "Dia da Consciência Negra", "national")));
		when(horarioComercialService.buscarTodosDiasComerciaisAtivos(true))
				.thenReturn(List.of(dia(1L, "Segunda-feira"), dia(5L, "Sexta-feira")));
		when(escalaService.buscarPorDiaComercial(5L))
				.thenReturn(List.of(new Escala(), new Escala()));

		List<AlertaFeriadoDTO> alertas = alertaFeriadoService.buscarAlertas(HOJE);

		assertEquals(1, alertas.size());
		AlertaFeriadoDTO alerta = alertas.get(0);
		assertEquals("20/11/2026", alerta.data());
		assertEquals("Dia da Consciência Negra", alerta.nome());
		assertEquals("Sexta-feira", alerta.diaComercial());
		assertEquals(2, alerta.totalAulas());
		verify(escalaService, never()).buscarPorDiaComercial(1L);
	}

	@Test
	void naoDeveAvisarQuandoNaoHaDiaComercialNaqueleDiaDaSemana() {
		when(feriadoService.buscarProximos(HOJE, AlertaFeriadoService.DIAS_A_FRENTE))
				.thenReturn(List.of(new FeriadoDTO("2026-11-20", "Dia da Consciência Negra", "national")));
		when(horarioComercialService.buscarTodosDiasComerciaisAtivos(true))
				.thenReturn(List.of(dia(1L, "Segunda-feira")));

		assertTrue(alertaFeriadoService.buscarAlertas(HOJE).isEmpty());
		verifyNoInteractions(escalaService);
	}

	@Test
	void naoDeveConsultarDiasComerciaisQuandoNaoHaFeriados() {
		when(feriadoService.buscarProximos(HOJE, AlertaFeriadoService.DIAS_A_FRENTE))
				.thenReturn(List.of());

		assertTrue(alertaFeriadoService.buscarAlertas(HOJE).isEmpty());
		verifyNoInteractions(horarioComercialService, escalaService);
	}

	@Test
	void deveConverterDescricaoDoDiaIgnorandoAcentosECaixa() {
		assertEquals(DayOfWeek.MONDAY, AlertaFeriadoService.converterDiaDaSemana("Segunda-feira"));
		assertEquals(DayOfWeek.TUESDAY, AlertaFeriadoService.converterDiaDaSemana("TERÇA"));
		assertEquals(DayOfWeek.WEDNESDAY, AlertaFeriadoService.converterDiaDaSemana("quarta"));
		assertEquals(DayOfWeek.THURSDAY, AlertaFeriadoService.converterDiaDaSemana("Quinta-feira"));
		assertEquals(DayOfWeek.FRIDAY, AlertaFeriadoService.converterDiaDaSemana("sexta"));
		assertEquals(DayOfWeek.SATURDAY, AlertaFeriadoService.converterDiaDaSemana("Sábado"));
		assertEquals(DayOfWeek.SUNDAY, AlertaFeriadoService.converterDiaDaSemana("Domingo"));
		assertNull(AlertaFeriadoService.converterDiaDaSemana("Dia do ensaio"));
		assertNull(AlertaFeriadoService.converterDiaDaSemana(null));
	}

}
