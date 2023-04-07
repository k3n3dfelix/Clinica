package com.dh.clinica.service;

import com.dh.clinica.controller.dto.request.PacienteRequest;
import com.dh.clinica.controller.dto.response.PacienteResponse;
import com.dh.clinica.controller.dto.request.update.PacienteRequestUpdate;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    PacienteResponse salvar(PacienteRequest request);

    List<PacienteResponse> buscarTodos();

    Optional<PacienteResponse> buscarPorId(Integer id);

    List<PacienteResponse> buscarPorNome(String name);

    PacienteResponse atualizar (PacienteRequestUpdate request);

    void excluir(Integer id);

}
