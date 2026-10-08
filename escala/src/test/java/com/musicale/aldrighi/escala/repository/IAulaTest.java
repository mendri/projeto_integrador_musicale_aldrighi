package com.musicale.aldrighi.escala.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.musicale.aldrighi.escala.model.Aula;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IAulaTest {
	@Autowired
	private IAula aulaRepository;

	@Test
	void deveSalvarAula() {

		Aula aula = new Aula();
		aula.setDescricao("Piano - Individual");
		aula.setIndividual(true);

		Aula salvo = aulaRepository.save(aula);

		assertNotNull(salvo.getId());
	}

	@Test
	void deveBuscarAulaPorId() {

		Aula aula1 = new Aula();
		aula1.setDescricao("Piano - Individual");
		aula1.setIndividual(true);

		Aula aula2 = new Aula();
		aula2.setDescricao("Violão - Individual");
		aula2.setIndividual(true);

		Aula salvo1 = aulaRepository.save(aula1);
		aulaRepository.save(aula2);

		Optional<Aula> aula = aulaRepository.findById(salvo1.getId());

		assertTrue(aula.isPresent());
		assertEquals(salvo1.getId(), aula.get().getId());
		assertEquals("Piano - Individual", aula.get().getDescricao());
	}

	@Test
	void deveBuscarAulaPorIndividual() {
		Aula aula1 = new Aula();
		aula1.setDescricao("Piano - Individual");
		aula1.setIndividual(true);

		Aula aula2 = new Aula();
		aula2.setDescricao("Violão - Coletiva");
		aula2.setIndividual(false);

		aulaRepository.save(aula1);
		aulaRepository.save(aula2);

		List<Aula> aulas = aulaRepository.findAllByIndividual(true);

		assertTrue(!aulas.isEmpty());
		assertEquals(true, aulas.stream().filter(aula -> aula.getDescricao().equals("Piano - Individual")).findFirst().isPresent());
	}
}