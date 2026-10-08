package com.musicale.aldrighi.escala.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.musicale.aldrighi.escala.model.Aluno;
import com.musicale.aldrighi.escala.model.Aula;
import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.Escala;
import com.musicale.aldrighi.escala.model.HorarioComercial;

@DataJpaTest 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IEscalaTest {

	@Autowired 
	private IEscala escalaRepository;

	@Autowired 
	private IHorarioComercial horarioComercialRepository;

	@Autowired 
	private IAula aulaRepository;

	@Autowired
	private IAluno alunoRepository;

	@Autowired 
	private IDiaComercial diaComercialRepository;

	Map<String, Long> preparaRepositorio() {
		DiaComercial dia = new DiaComercial();
		dia.setDescricao("Segunda-feira");
		dia.setAtivo(true);

		diaComercialRepository.save(dia);

		HorarioComercial horario = new HorarioComercial();
		horario.setHoraInicio("09:00");
		horario.setHoraFim("10:00");
		horario.setDiaComercial(dia);

		horarioComercialRepository.save(horario);

		Aula aula = new Aula();
		aula.setDescricao("Piano - Individual");
		aula.setIndividual(true);

		aulaRepository.save(aula);

		Aluno aluno = new Aluno();
		aluno.setNome("João");

		alunoRepository.save(aluno);

		Map<String, Long> ids = new HashMap<>();
		ids.put("horario", horario.getId());
		ids.put("aula", aula.getId());
		ids.put("aluno", aluno.getId());

		return ids;
	}

	@Test 
	void deveSalvarEscala() {
		Map<String, Long> ids = preparaRepositorio();

		Escala escala = new Escala();
		escala.setHorarioComercial(horarioComercialRepository.findById(ids.get("horario")).get());
		escala.setAula(aulaRepository.findById(ids.get("aula")).get());
		escala.setAluno(alunoRepository.findById(ids.get("aluno")).get());

		Escala salvo = escalaRepository.save(escala);

		assertNotNull(salvo.getId());
		assertEquals(ids.get("horario"), salvo.getHorarioComercial().getId());
		assertEquals(ids.get("aula"), salvo.getAula().getId());
		assertEquals(ids.get("aluno"), salvo.getAluno().getId());
	}

	@Test
	void deveBuscarEscalaPorHorarioComercialDiaComercialId() {
		Map<String, Long> ids = preparaRepositorio();

		Escala escala = new Escala();
		escala.setHorarioComercial(horarioComercialRepository.findById(ids.get("horario")).get());
		escala.setAula(aulaRepository.findById(ids.get("aula")).get());
		escala.setAluno(alunoRepository.findById(ids.get("aluno")).get());

		Escala salvo = escalaRepository.save(escala);

		assertNotNull(salvo.getId());
		assertEquals(ids.get("horario"), salvo.getHorarioComercial().getId());
		assertEquals(ids.get("aula"), salvo.getAula().getId());
		assertEquals(ids.get("aluno"), salvo.getAluno().getId());

		var escalas = escalaRepository.findByHorarioComercialDiaComercialId(escala.getHorarioComercial().getDiaComercial().getId());

		assertNotNull(escalas);
		assertEquals(1, escalas.size());
		assertEquals(salvo.getId(), escalas.get(0).getId());
	}

}
