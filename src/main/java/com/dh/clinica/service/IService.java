package com.dh.clinica.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

    T salvar(T t);

    List<T> buscarTodos();

    Optional<T> buscarPorId(Integer id);
    Optional<T> buscarPorNome(String name);

    T atualizar (T t);

    void excluir(Integer id);

}
