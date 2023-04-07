package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.request.DentistaRequest;
import com.dh.clinica.controller.dto.request.update.DentistaRequestUpdate;
import com.dh.clinica.controller.dto.response.DentistaResponse;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.service.IDentistaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DentistaServiceImpl implements IDentistaService {

    private IDentistaRepository dentistaRepository;

    @Autowired
    public DentistaServiceImpl(IDentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    @Override
    public DentistaResponse salvar(DentistaRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Dentista dentista = mapper.convertValue(request, Dentista.class);
        Dentista dentistaSalvo = dentistaRepository.save(dentista);
        DentistaResponse dentistaResponse = mapper.convertValue(dentistaSalvo, DentistaResponse.class);
        return dentistaResponse;
    }

    @Override
    public List<DentistaResponse> buscarTodos() {
        List<Dentista> dentistas = dentistaRepository.findAll();
        List<DentistaResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(Dentista dentista: dentistas){
            responses.add(mapper.convertValue(dentista, DentistaResponse.class));
        }
        return responses;
    }

    @Override
    public Optional<DentistaResponse> buscarPorId(Integer id) {
        Optional<Dentista> dentista = dentistaRepository.findById(id);
        ObjectMapper mapper = new ObjectMapper();
        return dentista.map(dentista1 -> mapper.convertValue(dentista1, DentistaResponse.class));
    }

    @Override
    public List<DentistaResponse> buscarPorNome(String name) {
        List<Dentista> dentistas = dentistaRepository.findDentistaByNomeContainingIgnoreCase(name);
        List<DentistaResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Dentista dentista: dentistas){
            responses.add(mapper.convertValue(dentista, DentistaResponse.class));
        }
        return responses;
    }

    @Override
    public DentistaResponse atualizar(DentistaRequestUpdate request) {
        ObjectMapper mapper = new ObjectMapper();
        Dentista dentista = mapper.convertValue(request, Dentista.class);
        Dentista dentistaSalvo = dentistaRepository.saveAndFlush(dentista);
        DentistaResponse dentistaResponse = mapper.convertValue(dentistaSalvo, DentistaResponse.class);
        return dentistaResponse;

    }

    @Override
    public void excluir(Integer id) {dentistaRepository.deleteById(id);
    }
}
