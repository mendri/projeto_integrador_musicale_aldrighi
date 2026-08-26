package com.musicale.aldrighi.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicale.aldrighi.escala.model.DiaComercial;
import com.musicale.aldrighi.escala.model.HorarioComercial;
import com.musicale.aldrighi.escala.repository.IDiaComercial;
import com.musicale.aldrighi.escala.repository.IHorarioComercial;

@Service
public class HorarioComercialService {

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

}
