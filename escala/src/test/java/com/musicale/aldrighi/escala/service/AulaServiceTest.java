package com.musicale.aldrighi.escala.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musicale.aldrighi.escala.model.Aula;
import com.musicale.aldrighi.escala.repository.IAula;

@ExtendWith(MockitoExtension.class)
public class AulaServiceTest {

	@Mock
	private IAula repository;

	@InjectMocks
	private AulaService aulaService;

	private Aula aula;

	@BeforeEach
	void setUp() {
		aula = new Aula();
		aula.setDescricao("Piano - Individual");
		aula.setIndividual(true);
	}

	@Test
	void deveBuscarTodasAsAulas() {
		Aula aula2 = new Aula();
		aula2.setDescricao("Violão - Individual");
		aula2.setIndividual(true);

		List<Aula> aulasEsperadas = Arrays.asList(aula, aula2);

		when(repository.findAll()).thenReturn(aulasEsperadas);

		List<Aula> resultado = aulaService.buscarTodos();

		assertEquals(2, resultado.size());
		assertEquals(aulasEsperadas, resultado);
		verify(repository).findAll();
	}

	@Test
	void deveSalvarAula() {
		aulaService.salvar(aula);

		verify(repository).save(aula);
	}

	@Test
	void deveDeletarAulaPorId() {
		Long id = 1L;

		aulaService.deletar(id);

		verify(repository).deleteById(id);
	}
}
