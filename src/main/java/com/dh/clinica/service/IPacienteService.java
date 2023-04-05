package com.dh.clinica.service;

import com.dh.clinica.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente salvar(Paciente paciente);

    List<Paciente> buscarTodos();

    Optional<Paciente> buscarPorId(Integer id);

    List<Paciente> buscarPorNome(String name);

    Paciente atualizar (Paciente paciente);

    void excluir(Integer id);

}
