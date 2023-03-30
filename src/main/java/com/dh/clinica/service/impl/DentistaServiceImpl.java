package com.dh.clinica.service.impl;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaServiceImpl implements IService<Dentista> {


    private IDentistaRepository dentistaRepository;

    @Autowired
    public DentistaServiceImpl(IDentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    @Override
    public Dentista salvar(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }

    @Override
    public List<Dentista> buscarTodos() {
        return dentistaRepository.findAll();
    }

    @Override
    public Optional<Dentista> buscarPorId(Integer id) {
        return dentistaRepository.findById(id);
    }

    @Override
    public List<Dentista> buscarPorNome(String nome) {
        return dentistaRepository.findDentistaByNomeContainingIgnoreCase(nome);
    }

    @Override
    public Dentista atualizar(Dentista dentista) {
        return dentistaRepository.saveAndFlush(dentista);
    }

    @Override
    public void excluir(Integer id) {
        dentistaRepository.deleteById(id);
    }
}
