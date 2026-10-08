package com.musicale.aldrighi.escala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicale.aldrighi.escala.model.Aula;

public interface IAula extends JpaRepository<Aula, Long> {

	List<Aula> findAllByIndividual(boolean b);

}
