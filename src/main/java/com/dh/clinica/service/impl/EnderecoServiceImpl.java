package com.dh.clinica.service.impl;

import com.dh.clinica.model.Endereco;
import com.dh.clinica.repository.IEnderecoRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements IService<Endereco> {


    private IEnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoServiceImpl(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public List<Endereco> buscarTodos() {
        return enderecoRepository.findAll();
    }

    @Override
    public Optional<Endereco> buscarPorId(Integer id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public Optional<Endereco> buscarPorNome(String name) {
        return enderecoRepository.findEnderecoByRuaContainingIgnoreCase(name);
    }

    @Override
    public Endereco atualizar(Endereco endereco) {
        return enderecoRepository.saveAndFlush(endereco);
    }

    @Override
    public void excluir(Integer id) {
        enderecoRepository.deleteById(id);
    }
}