package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.request.ConsultaRequest;
import com.dh.clinica.controller.dto.response.ConsultaResponse;
import com.dh.clinica.controller.dto.request.update.ConsultaRequestUpdate;
import com.dh.clinica.controller.dto.response.ConsultaResponseCadastro;
import com.dh.clinica.model.Consulta;
import com.dh.clinica.repository.IConsultaRepository;
import com.dh.clinica.service.IConsultaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private IConsultaRepository consultaRepository;

    @Autowired
    public ConsultaServiceImpl(IConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public ConsultaResponseCadastro salvar(ConsultaRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Consulta consulta = mapper.convertValue(request, Consulta.class);
        Consulta consultaSalvo = consultaRepository.save(consulta);
        ConsultaResponseCadastro consultaResponseCadastro = mapper.convertValue(consultaSalvo, ConsultaResponseCadastro.class);
        return consultaResponseCadastro;
    }

    @Override
    public List<ConsultaResponse> buscarTodos() {
        List<Consulta> consultas = consultaRepository.findAll();
        List<ConsultaResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(Consulta consulta: consultas){
            responses.add(mapper.convertValue(consulta, ConsultaResponse.class));
        }
        return responses;
    }

    @Override
    public Optional<ConsultaResponse> buscarPorId(Integer id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        ObjectMapper mapper = new ObjectMapper();
        return consulta.map(consulta1 -> mapper.convertValue(consulta1, ConsultaResponse.class));
    }


    @Override
    public ConsultaResponse atualizar(ConsultaRequestUpdate request) {
        ObjectMapper mapper = new ObjectMapper();
        Consulta consulta = mapper.convertValue(request, Consulta.class);
        Consulta consultaSalvo = consultaRepository.saveAndFlush(consulta);
        ConsultaResponse consultaResponse = mapper.convertValue(consultaSalvo, ConsultaResponse.class);
        return consultaResponse;

    }

    @Override
    public void excluir(Integer id) {consultaRepository.deleteById(id);
    }


}
