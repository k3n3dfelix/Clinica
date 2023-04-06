package com.dh.clinica.service;

import com.dh.clinica.controller.dto.request.EnderecoRequest;
import com.dh.clinica.controller.dto.response.EnderecoResponse;

import java.util.List;
import java.util.Optional;

public interface IEnderecoService {

    EnderecoResponse salvar(EnderecoRequest request);

    List<EnderecoResponse> buscarTodos();

    Optional<EnderecoResponse> buscarPorId(Integer id);

    List<EnderecoResponse> buscarPorRua(String rua);

    EnderecoResponse atualizar (EnderecoRequest request);

    void excluir(Integer id);

}
