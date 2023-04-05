package com.dh.clinica.service;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    UsuarioResponse salvar(UsuarioRequest request);

    List<UsuarioResponse> buscarTodos();

    Optional<UsuarioResponse> buscarPorId(Integer id);

    List<UsuarioResponse> buscarPorNome(String name);

    UsuarioResponse atualizar (UsuarioRequest request);

    void excluir(Integer id);

}
