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

import com.musicale.aldrighi.escala.model.DiaComercial;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IDiaComercialTest {
	@Autowired
	private IDiaComercial diaComercialRepository;

	@Test
	void deveSalvarDiaComercial() {

		DiaComercial dia = new DiaComercial();
		dia.setDescricao("Segunda-feira");
		dia.setAtivo(true);

		DiaComercial salvo = diaComercialRepository.save(dia);

		assertNotNull(salvo.getId());
	}

	@Test
	void deveBuscarDiaComercialPorId() {

		DiaComercial dia1 = new DiaComercial();
		dia1.setDescricao("Segunda-feira");
		dia1.setAtivo(true);

		DiaComercial dia2 = new DiaComercial();
		dia2.setDescricao("Terça-feira");
		dia2.setAtivo(true);

		DiaComercial salvo1 = diaComercialRepository.save(dia1);
		diaComercialRepository.save(dia2);

		Optional<DiaComercial> dia = diaComercialRepository.findById(salvo1.getId());

		assertTrue(dia.isPresent());
		assertEquals(salvo1.getId(), dia.get().getId());
		assertEquals("Segunda-feira", dia.get().getDescricao());
	}

	@Test
	void deveBuscarDiaComercialPorAtivo() {
		DiaComercial dia1 = new DiaComercial();
		dia1.setDescricao("Segunda-feira");
		dia1.setAtivo(true);

		DiaComercial dia2 = new DiaComercial();
		dia2.setDescricao("Terça-feira");
		dia2.setAtivo(false);

		diaComercialRepository.save(dia1);
		diaComercialRepository.save(dia2);

		List<DiaComercial> dias = diaComercialRepository.findAllByAtivo(true);

		assertTrue(!dias.isEmpty());
		assertEquals(true, dias.stream().filter(dia -> dia.getDescricao().equals("Segunda-feira")).findFirst().isPresent());
	}
}
