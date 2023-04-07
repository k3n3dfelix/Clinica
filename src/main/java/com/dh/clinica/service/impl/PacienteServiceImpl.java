package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.request.PacienteRequest;
import com.dh.clinica.controller.dto.response.PacienteResponse;
import com.dh.clinica.controller.dto.request.update.PacienteRequestUpdate;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private IPacienteRepository pacienteRepository;

    @Autowired
    public PacienteServiceImpl(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteResponse salvar(PacienteRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Paciente paciente = mapper.convertValue(request, Paciente.class);
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        PacienteResponse pacienteResponse = mapper.convertValue(pacienteSalvo, PacienteResponse.class);
        return pacienteResponse;
    }

    @Override
    public List<PacienteResponse> buscarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(Paciente paciente: pacientes){
            responses.add(mapper.convertValue(paciente, PacienteResponse.class));
        }
        return responses;
    }

    @Override
    public Optional<PacienteResponse> buscarPorId(Integer id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        ObjectMapper mapper = new ObjectMapper();
        return paciente.map(paciente1 -> mapper.convertValue(paciente1, PacienteResponse.class));
    }

    @Override
    public List<PacienteResponse> buscarPorNome(String name) {
        List<Paciente> pacientes = pacienteRepository.findPacienteByNomeContainingIgnoreCase(name);
        List<PacienteResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Paciente paciente: pacientes){
            responses.add(mapper.convertValue(paciente, PacienteResponse.class));
        }
        return responses;
    }

    @Override
    public PacienteResponse atualizar(PacienteRequestUpdate request) {
        ObjectMapper mapper = new ObjectMapper();
        Paciente paciente = mapper.convertValue(request, Paciente.class);
        Paciente pacienteSalvo = pacienteRepository.saveAndFlush(paciente);
        PacienteResponse pacienteResponse = mapper.convertValue(pacienteSalvo, PacienteResponse.class);
        return pacienteResponse;

    }

    @Override
    public void excluir(Integer id) {pacienteRepository.deleteById(id);
    }

}

