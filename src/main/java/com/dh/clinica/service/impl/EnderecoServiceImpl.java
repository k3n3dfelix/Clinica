package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.request.EnderecoRequest;
import com.dh.clinica.controller.dto.response.EnderecoResponse;
import com.dh.clinica.model.Endereco;
import com.dh.clinica.repository.IEnderecoRepository;
import com.dh.clinica.service.IEnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements IEnderecoService {

    private IEnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoServiceImpl(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public EnderecoResponse salvar(EnderecoRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Endereco endereco = mapper.convertValue(request, Endereco.class);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        EnderecoResponse enderecoResponse = mapper.convertValue(enderecoSalvo, EnderecoResponse.class);
        return enderecoResponse;
    }

    @Override
    public List<EnderecoResponse> buscarTodos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        List<EnderecoResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(Endereco endereco: enderecos){
            responses.add(mapper.convertValue(endereco, EnderecoResponse.class));
        }
        return responses;
    }

    @Override
    public Optional<EnderecoResponse> buscarPorId(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        ObjectMapper mapper = new ObjectMapper();
        return endereco.map(endereco1 -> mapper.convertValue(endereco1, EnderecoResponse.class));
    }

    @Override
    public List<EnderecoResponse> buscarPorRua(String rua) {
        List<Endereco> enderecos = enderecoRepository.findEnderecoByRuaContainingIgnoreCase(rua);
        List<EnderecoResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Endereco endereco: enderecos){
            responses.add(mapper.convertValue(endereco, EnderecoResponse.class));
        }
        return responses;
    }

    @Override
    public EnderecoResponse atualizar(EnderecoRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Endereco endereco = mapper.convertValue(request, Endereco.class);
        Endereco enderecoSalvo = enderecoRepository.saveAndFlush(endereco);
        EnderecoResponse enderecoResponse = mapper.convertValue(enderecoSalvo, EnderecoResponse.class);
        return enderecoResponse;

    }

    @Override
    public void excluir(Integer id) {enderecoRepository.deleteById(id);
    }

}