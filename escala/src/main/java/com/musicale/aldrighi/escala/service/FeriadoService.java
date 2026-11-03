package com.musicale.aldrighi.escala.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.musicale.aldrighi.escala.dto.FeriadoDTO;

@Service
public class FeriadoService {

	private final RestClient restClient;

	private final Map<Integer, List<FeriadoDTO>> cachePorAno = new ConcurrentHashMap<>();

	@Autowired
	public FeriadoService(@Value("${feriados.api.url:https://brasilapi.com.br/api/feriados/v1}") String baseUrl) {
		this(RestClient.builder().baseUrl(baseUrl));
	}

	FeriadoService(RestClient.Builder builder) {
		this.restClient = builder.build();
	}

	public List<FeriadoDTO> buscarPorAno(int ano) {
		List<FeriadoDTO> emCache = cachePorAno.get(ano);
		if (emCache != null) {
			return emCache;
		}

		try {
			FeriadoDTO[] resposta = restClient.get()
					.uri("/{ano}", ano)
					.retrieve()
					.body(FeriadoDTO[].class);

			List<FeriadoDTO> feriados = resposta == null ? List.of() : List.of(resposta);
			cachePorAno.put(ano, feriados);
			return feriados;
		} catch (RestClientException e) {
			return List.of();
		}
	}

	public List<FeriadoDTO> buscarProximos(LocalDate inicio, int dias) {
		LocalDate fim = inicio.plusDays(dias);
		List<FeriadoDTO> resultado = new ArrayList<>();

		for (int ano = inicio.getYear(); ano <= fim.getYear(); ano++) {
			for (FeriadoDTO feriado : buscarPorAno(ano)) {
				LocalDate data = converterData(feriado.date());
				if (data != null && !data.isBefore(inicio) && !data.isAfter(fim)) {
					resultado.add(feriado);
				}
			}
		}

		resultado.sort(Comparator.comparing(f -> converterData(f.date())));
		return resultado;
	}

	public static LocalDate converterData(String texto) {
		try {
			return texto == null ? null : LocalDate.parse(texto);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

}
