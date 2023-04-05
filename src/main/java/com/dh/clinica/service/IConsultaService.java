package com.dh.clinica.service;

import com.dh.clinica.model.Consulta;

import java.util.List;
import java.util.Optional;

public interface IConsultaService {

    Consulta salvar(Consulta consulta);

    List<Consulta> buscarTodos();

    Optional<Consulta> buscarPorId(Integer id);

    List<Consulta> buscarPorNome(String name);

    Consulta atualizar (Consulta consulta);

    void excluir(Integer id);

}
