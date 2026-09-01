package com.musicale.aldrighi.escala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicale.aldrighi.escala.model.DiaComercial;

public interface IDiaComercial extends JpaRepository<DiaComercial, Long> {

	List<DiaComercial> findAllByAtivoTrue();

}
