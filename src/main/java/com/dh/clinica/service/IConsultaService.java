package com.dh.clinica.service;

import com.dh.clinica.controller.dto.request.ConsultaRequest;
import com.dh.clinica.controller.dto.response.ConsultaResponse;
import com.dh.clinica.controller.dto.request.update.ConsultaRequestUpdate;
import com.dh.clinica.controller.dto.response.ConsultaResponseCadastro;

import java.util.List;
import java.util.Optional;

public interface IConsultaService {

    ConsultaResponseCadastro salvar(ConsultaRequest request);

    List<ConsultaResponse> buscarTodos();

    Optional<ConsultaResponse> buscarPorId(Integer id);

    ConsultaResponse atualizar (ConsultaRequestUpdate request);

    void excluir(Integer id);


}
