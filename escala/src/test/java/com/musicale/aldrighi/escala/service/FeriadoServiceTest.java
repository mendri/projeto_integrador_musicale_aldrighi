package com.musicale.aldrighi.escala.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.musicale.aldrighi.escala.dto.FeriadoDTO;

class FeriadoServiceTest {

	private static final String BASE_URL = "https://brasilapi.com.br/api/feriados/v1";

	private static final String JSON_2026 = """
			[
			  {"date":"2026-01-01","name":"Confraternização Universal","type":"national"},
			  {"date":"2026-11-02","name":"Finados","type":"national"},
			  {"date":"2026-11-20","name":"Dia da Consciência Negra","type":"national"},
			  {"date":"2026-12-25","name":"Natal","type":"national"}
			]
			""";

	private static final String JSON_2027 = """
			[
			  {"date":"2027-01-01","name":"Confraternização Universal","type":"national"}
			]
			""";

	private MockRestServiceServer server;

	private FeriadoService service;

	@BeforeEach
	void setUp() {
		RestClient.Builder builder = RestClient.builder().baseUrl(BASE_URL);
		server = MockRestServiceServer.bindTo(builder).build();
		service = new FeriadoService(builder);
	}

	@Test
	void deveBuscarFeriadosDoAno() {
		server.expect(requestTo(BASE_URL + "/2026"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(JSON_2026, MediaType.APPLICATION_JSON));

		List<FeriadoDTO> feriados = service.buscarPorAno(2026);

		assertEquals(4, feriados.size());
		assertEquals("Finados", feriados.get(1).name());
		server.verify();
	}

	@Test
	void deveFiltrarFeriadosDentroDoPeriodoEmOrdemDeData() {
		server.expect(requestTo(BASE_URL + "/2026"))
				.andRespond(withSuccess(JSON_2026, MediaType.APPLICATION_JSON));

		List<FeriadoDTO> feriados = service.buscarProximos(LocalDate.of(2026, 11, 3), 55);

		assertEquals(2, feriados.size());
		assertEquals("Dia da Consciência Negra", feriados.get(0).name());
		assertEquals("Natal", feriados.get(1).name());
		server.verify();
	}

	@Test
	void deveConsultarOAnoSeguinteQuandoOPeriodoVirarOAno() {
		server.expect(requestTo(BASE_URL + "/2026"))
				.andRespond(withSuccess(JSON_2026, MediaType.APPLICATION_JSON));
		server.expect(requestTo(BASE_URL + "/2027"))
				.andRespond(withSuccess(JSON_2027, MediaType.APPLICATION_JSON));

		List<FeriadoDTO> feriados = service.buscarProximos(LocalDate.of(2026, 12, 20), 30);

		assertEquals(2, feriados.size());
		assertEquals("2026-12-25", feriados.get(0).date());
		assertEquals("2027-01-01", feriados.get(1).date());
		server.verify();
	}

	@Test
	void deveUsarCacheNaSegundaConsultaDoMesmoAno() {
		server.expect(requestTo(BASE_URL + "/2026"))
				.andRespond(withSuccess(JSON_2026, MediaType.APPLICATION_JSON));

		service.buscarPorAno(2026);
		List<FeriadoDTO> segundaChamada = service.buscarPorAno(2026);

		assertEquals(4, segundaChamada.size());
		server.verify(); // só uma requisição foi esperada e feita
	}

	@Test
	void deveDevolverListaVaziaQuandoAApiFalha() {
		server.expect(requestTo(BASE_URL + "/2026"))
				.andRespond(withServerError());

		List<FeriadoDTO> feriados = service.buscarPorAno(2026);

		assertTrue(feriados.isEmpty());
		server.verify();
	}

}
