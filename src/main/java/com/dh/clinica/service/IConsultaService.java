package com.dh.clinica.service;

import com.dh.clinica.controller.dto.request.ConsultaRequest;
import com.dh.clinica.controller.dto.response.ConsultaResponse;

import java.util.List;
import java.util.Optional;

public interface IConsultaService {

    ConsultaResponse salvar(ConsultaRequest request);

    List<ConsultaResponse> buscarTodos();

    Optional<ConsultaResponse> buscarPorId(Integer id);

    ConsultaResponse atualizar (ConsultaRequest request);

    void excluir(Integer id);


}
