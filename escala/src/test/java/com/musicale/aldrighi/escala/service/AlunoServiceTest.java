package com.musicale.aldrighi.escala.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musicale.aldrighi.escala.model.Aluno;
import com.musicale.aldrighi.escala.repository.IAluno;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private IAluno repository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setNome("João Silva");
    }

    @Test
    void deveBuscarTodosOsAlunos() {
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Souza");

        List<Aluno> alunosEsperados = Arrays.asList(aluno, aluno2);

        when(repository.findAll()).thenReturn(alunosEsperados);

        List<Aluno> resultado = alunoService.buscarTodos();

        assertEquals(2, resultado.size());
        assertEquals(alunosEsperados, resultado);
        verify(repository).findAll();
    }

    @Test
    void deveSalvarAluno() {
        alunoService.salvar(aluno);

        verify(repository).save(aluno);
    }

    @Test
    void deveDeletarAlunoPorId() {
        Long id = 1L;

        alunoService.deletar(id);

        verify(repository).deleteById(id);
    }
}