package com.dh.clinica.service;

import com.dh.clinica.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface IEnderecoService {

    Endereco salvar(Endereco endereco);

    List<Endereco> buscarTodos();

    Optional<Endereco> buscarPorId(Integer id);

    List<Endereco> buscarPorNome(String name);

    Endereco atualizar (Endereco endereco);

    void excluir(Integer id);

}
