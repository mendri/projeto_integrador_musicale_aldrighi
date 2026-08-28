package com.musicale.aldrighi.escala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicale.aldrighi.escala.model.Aluno;

public interface IAluno extends JpaRepository<Aluno, Long> {

}
