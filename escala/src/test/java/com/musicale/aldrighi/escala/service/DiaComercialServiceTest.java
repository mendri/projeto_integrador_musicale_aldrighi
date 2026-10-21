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

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.repository.IDiaComercial;

@ExtendWith(MockitoExtension.class)
public class DiaComercialServiceTest {

	@Mock
	private IDiaComercial repository;

	@InjectMocks
	private DiaComercialService diaComercialService;

	private DiaComercial diaComercial;

	@BeforeEach
	void setUp() {
		diaComercial = new DiaComercial();
		diaComercial.setAtivo(false);
	}

	@Test
	void deveBuscarTodosOsDiasComerciais() {
		DiaComercial dia2 = new DiaComercial();
		dia2.setAtivo(true);

		List<DiaComercial> diasEsperados = Arrays.asList(diaComercial, dia2);

		when(repository.findAll()).thenReturn(diasEsperados);

		List<DiaComercial> resultado = diaComercialService.buscarTodos();

		assertEquals(2, resultado.size());
		assertEquals(diasEsperados, resultado);
		verify(repository).findAll();
	}

	@Test
	void deveAtivarDiaComercial() {
		Long id = 1L;

		when(repository.findById(id))
				.thenReturn(Optional.of(diaComercial));

		diaComercialService.editar(id, true);

		assertTrue(diaComercial.getAtivo());

		verify(repository).findById(id);
		verify(repository).save(diaComercial);
	}

	@Test
	void deveDesativarDiaComercial() {
		Long id = 1L;
		diaComercial.setAtivo(true);

		when(repository.findById(id))
				.thenReturn(Optional.of(diaComercial));

		diaComercialService.editar(id, false);

		assertFalse(diaComercial.getAtivo());

		verify(repository).findById(id);
		verify(repository).save(diaComercial);
	}

	@Test
	void deveLancarExcecaoQuandoDiaComercialNaoExiste() {
		Long id = 99L;

		when(repository.findById(id))
				.thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> diaComercialService.editar(id, true));

		assertEquals(
				"Dia comercial não encontrado",
				exception.getMessage());

		verify(repository).findById(id);
		verify(repository, never()).save(any(DiaComercial.class));
	}
}
