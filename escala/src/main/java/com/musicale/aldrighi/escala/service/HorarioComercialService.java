package com.musicale.aldrighi.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.repository.IDiaComercial;
import com.musicale.aldrighi.escala.repository.IHorarioComercial;

@Service
public class HorarioComercialService {

	private static final String REGEX_HORARIO = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

	private final IHorarioComercial repository;

	private final IDiaComercial diaComercialRepository;

	public HorarioComercialService(IHorarioComercial repository, IDiaComercial diaComercialRepository) {
		this.repository = repository;
		this.diaComercialRepository = diaComercialRepository;
	}

	public List<HorarioComercial> buscarTodos() {
		return repository.findAll();
	}

	public List<HorarioComercial> buscarPorDiaComercial(Long idDiaComercial) {
		return repository.findAllByDiaComercial_Id(idDiaComercial);
	}

	public List<DiaComercial> buscarTodosDiasComerciais() {
		return diaComercialRepository.findAll();
	}

	public List<DiaComercial> buscarTodosDiasComerciaisAtivos() {
		return diaComercialRepository.findAllByAtivoTrue();
	}

	public void salvar(HorarioComercial horarioComercial) {
		repository.save(horarioComercial);
	}

	public String validarHorario(HorarioComercial horarioComercial) {
		if (!horarioValido(horarioComercial.getHoraInicio()) || !horarioValido(horarioComercial.getHoraFim())) {
			return "Horário inválido. O formato deve ser HH:mm.";
		}

		List<HorarioComercial> horariosExistentesDia = repository.findAllByDiaComercial_Id(horarioComercial.getDiaComercial().getId());

		for (HorarioComercial horarioExistente : horariosExistentesDia) {
			if (horarioComercial.getHoraInicio().compareTo(horarioExistente.getHoraFim()) < 0 &&
				horarioComercial.getHoraFim().compareTo(horarioExistente.getHoraInicio()) > 0) {
				return "O horário informado entra em conflito com um horário existente.";
			}
		}

		return null;
	}

	public boolean horarioValido(String horario) {
		return horario != null && horario.matches(REGEX_HORARIO);
	}
}
