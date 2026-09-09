package com.musicale.aldrighi.escala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicale.aldrighi.escala.model.Escala;

public interface IEscala extends JpaRepository<Escala, Long> {

	List<Escala> findByHorarioComercialDiaComercialId(Long id);

}
