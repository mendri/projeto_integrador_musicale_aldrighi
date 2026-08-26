package com.musicale.aldrighi.escala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicale.aldrighi.escala.model.HorarioComercial;

public interface IHorarioComercial extends JpaRepository<HorarioComercial, Long> {

	List<HorarioComercial> findAllByDiaComercial_Id(Long idDiaComercial);

}
