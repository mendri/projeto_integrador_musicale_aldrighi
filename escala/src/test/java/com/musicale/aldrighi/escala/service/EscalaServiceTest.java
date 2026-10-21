package com.musicale.aldrighi.escala.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musicale.aldrighi.escala.model.Escala;
import com.musicale.aldrighi.escala.repository.IEscala;

@ExtendWith(MockitoExtension.class)
public class EscalaServiceTest {

	@Mock
	private IEscala repository;

	@InjectMocks
	private EscalaService escalaService;

	private Escala escala;

	@BeforeEach
	void setUp() {
		escala = new Escala();
	}

	@Test
	void deveBuscarTodasAsEscalas() {
		Escala escala2 = new Escala();

		List<Escala> escalasEsperadas = Arrays.asList(escala, escala2);

		when(repository.findAll()).thenReturn(escalasEsperadas);

		List<Escala> resultado = escalaService.buscarTodos();

		assertEquals(2, resultado.size());
		assertEquals(escalasEsperadas, resultado);
		verify(repository).findAll();
	}

	@Test
	void deveBuscarEscalasPorDiaComercial() {
		Long diaComercialId = 1L;

		List<Escala> escalasEsperadas = List.of(escala);

		when(repository.findByHorarioComercialDiaComercialId(diaComercialId))
				.thenReturn(escalasEsperadas);

		List<Escala> resultado = escalaService.buscarPorDiaComercial(diaComercialId);

		assertEquals(1, resultado.size());
		assertEquals(escalasEsperadas, resultado);

		verify(repository)
				.findByHorarioComercialDiaComercialId(diaComercialId);
	}

	@Test
	void deveSalvarERetornarEscala() {
		Long id = 1L;

		Escala escalaSalva = new Escala();
		escalaSalva.setId(id);

		when(repository.save(escala)).thenReturn(escalaSalva);
		when(repository.findById(id)).thenReturn(Optional.of(escalaSalva));

		Escala resultado = escalaService.salvar(escala);

		assertNotNull(resultado);
		assertEquals(id, resultado.getId());

		verify(repository).save(escala);
		verify(repository).findById(id);
	}

	@Test
	void deveLancarExcecaoQuandoEscalaSalvaNaoForEncontrada() {
		Long id = 1L;

		Escala escalaSalva = new Escala();
		escalaSalva.setId(id);

		when(repository.save(escala)).thenReturn(escalaSalva);
		when(repository.findById(id)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> escalaService.salvar(escala));

		assertEquals("Erro ao salvar a escala", exception.getMessage());

		verify(repository).save(escala);
		verify(repository).findById(id);
	}

	@Test
	void deveRemoverEscalaPorId() {
		Long id = 1L;

		escalaService.remover(id);

		verify(repository).deleteById(id);
	}
}
