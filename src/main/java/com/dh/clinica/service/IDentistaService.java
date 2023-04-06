package com.dh.clinica.service;

import com.dh.clinica.controller.dto.request.DentistaRequest;
import com.dh.clinica.controller.dto.response.DentistaResponse;
import com.dh.clinica.controller.dto.update.DentistaRequestUpdate;

import java.util.List;
import java.util.Optional;

public interface IDentistaService {

    DentistaResponse salvar(DentistaRequest request);

    List<DentistaResponse> buscarTodos();

    Optional<DentistaResponse> buscarPorId(Integer id);

    List<DentistaResponse> buscarPorNome(String name);

    DentistaResponse atualizar (DentistaRequestUpdate request);

    void excluir(Integer id);



}
