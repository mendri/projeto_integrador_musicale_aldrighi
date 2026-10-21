package com.musicale.aldrighi.escala.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.musicale.aldrighi.escala.model.Aluno;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IAlunoTest {

	@Autowired
	private IAluno alunoRepository;

	@Test
	void deveSalvarAluno() {

		Aluno aluno = new Aluno();
		aluno.setNome("João");

		Aluno salvo = alunoRepository.save(aluno);

		assertNotNull(salvo.getId());
	}

	@Test
	void deveBuscarAlunoPorId() {

		Aluno aluno1 = new Aluno();
		aluno1.setNome("João");

		Aluno aluno2 = new Aluno();
		aluno2.setNome("Maria");

		Aluno salvo1 = alunoRepository.save(aluno1);
		alunoRepository.save(aluno2);

		Optional<Aluno> aluno = alunoRepository.findById(salvo1.getId());

		assertTrue(aluno.isPresent());
		assertEquals(salvo1.getId(), aluno.get().getId());
		assertEquals("João", aluno.get().getNome());
	}
}