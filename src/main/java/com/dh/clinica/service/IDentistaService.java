package com.dh.clinica.service;

import com.dh.clinica.model.Dentista;

import java.util.List;
import java.util.Optional;

public interface IDentistaService {

    Dentista salvar(Dentista dentista);

    List<Dentista> buscarTodos();

    Optional<Dentista> buscarPorId(Integer id);

    List<Dentista> buscarPorNome(String name);

    Dentista atualizar (Dentista dentista);

    void excluir(Integer id);

}
