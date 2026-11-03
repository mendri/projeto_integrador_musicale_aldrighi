package com.musicale.aldrighi.escala.service;

import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.dto.AlertaFeriadoDTO;
import com.musicale.aldrighi.escala.dto.FeriadoDTO;
import com.musicale.aldrighi.escala.model.DiaComercial;

/**
 * Cruza os feriados da BrasilAPI com os dias comerciais ativos da escola
 * para avisar quais aulas da escala semanal serão afetadas.
 */
@Service
public class AlertaFeriadoService {

	public static final int DIAS_A_FRENTE = 60;

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final ZoneId FUSO = ZoneId.of("America/Sao_Paulo");

	private final FeriadoService feriadoService;

	private final HorarioComercialService horarioComercialService;

	private final EscalaService escalaService;

	public AlertaFeriadoService(FeriadoService feriadoService, HorarioComercialService horarioComercialService,
			EscalaService escalaService) {
		this.feriadoService = feriadoService;
		this.horarioComercialService = horarioComercialService;
		this.escalaService = escalaService;
	}

	public List<AlertaFeriadoDTO> buscarAlertas() {
		return buscarAlertas(LocalDate.now(FUSO));
	}

	public List<AlertaFeriadoDTO> buscarAlertas(LocalDate hoje) {
		List<FeriadoDTO> feriados = feriadoService.buscarProximos(hoje, DIAS_A_FRENTE);
		if (feriados.isEmpty()) {
			return List.of();
		}

		List<DiaComercial> diasAtivos = horarioComercialService.buscarTodosDiasComerciaisAtivos(true);
		List<AlertaFeriadoDTO> alertas = new ArrayList<>();

		for (FeriadoDTO feriado : feriados) {
			LocalDate data = FeriadoService.converterData(feriado.date());
			if (data == null) {
				continue;
			}

			for (DiaComercial dia : diasAtivos) {
				if (data.getDayOfWeek() == converterDiaDaSemana(dia.getDescricao())) {
					int totalAulas = escalaService.buscarPorDiaComercial(dia.getId()).size();
					alertas.add(new AlertaFeriadoDTO(data.format(FORMATO_DATA), feriado.name(), dia.getDescricao(),
							totalAulas));
				}
			}
		}

		return alertas;
	}

	/**
	 * Converte a descrição livre do dia comercial ("Segunda-feira", "terça", "Sábado"...)
	 * no dia da semana correspondente. Devolve null se não reconhecer.
	 */
	static DayOfWeek converterDiaDaSemana(String descricao) {
		if (descricao == null) {
			return null;
		}

		String texto = Normalizer.normalize(descricao, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.trim()
				.toLowerCase();

		if (texto.startsWith("segunda")) return DayOfWeek.MONDAY;
		if (texto.startsWith("terca")) return DayOfWeek.TUESDAY;
		if (texto.startsWith("quarta")) return DayOfWeek.WEDNESDAY;
		if (texto.startsWith("quinta")) return DayOfWeek.THURSDAY;
		if (texto.startsWith("sexta")) return DayOfWeek.FRIDAY;
		if (texto.startsWith("sabado")) return DayOfWeek.SATURDAY;
		if (texto.startsWith("domingo")) return DayOfWeek.SUNDAY;
		return null;
	}

}
