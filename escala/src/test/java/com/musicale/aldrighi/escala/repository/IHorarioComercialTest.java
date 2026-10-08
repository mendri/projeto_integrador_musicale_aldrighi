package com.musicale.aldrighi.escala.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.HorarioComercial;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IHorarioComercialTest {

	@Autowired
	private IHorarioComercial horarioComercialRepository;
	@Autowired
	private IDiaComercial diaComercialRepository;

	@Test
	void deveSalvarHorarioComercial() {
		DiaComercial dia = new DiaComercial();
		dia.setDescricao("Segunda-feira");
		dia.setAtivo(true);

		diaComercialRepository.save(dia);

		HorarioComercial horario = new HorarioComercial();
		horario.setHoraInicio("09:00");
		horario.setHoraFim("10:00");
		horario.setDiaComercial(dia);

		HorarioComercial salvo = horarioComercialRepository.save(horario);

		assertNotNull(salvo.getId());
		assertEquals("09:00", salvo.getHoraInicio());
		assertEquals("10:00", salvo.getHoraFim());
	}

	@Test
	void deveBuscarHorarioComercialPorDiaComercialId() {
		DiaComercial dia = new DiaComercial();
		dia.setDescricao("Segunda-feira");
		dia.setAtivo(true);

		diaComercialRepository.save(dia);

		HorarioComercial horario1 = new HorarioComercial();
		horario1.setHoraInicio("09:00");
		horario1.setHoraFim("10:00");
		horario1.setDiaComercial(dia);

		HorarioComercial horario2 = new HorarioComercial();
		horario2.setHoraInicio("10:00");
		horario2.setHoraFim("11:00");
		horario2.setDiaComercial(dia);

		HorarioComercial salvo1 = horarioComercialRepository.save(horario1);
		HorarioComercial salvo2 = horarioComercialRepository.save(horario2);

		var horarios = horarioComercialRepository.findAllByDiaComercial_Id(dia.getId());

		assertNotNull(horarios);
		assertEquals(2, horarios.size());
		assertEquals(salvo1.getId(), horarios.get(0).getId());
		assertEquals(salvo2.getId(), horarios.get(1).getId());
	}
}
