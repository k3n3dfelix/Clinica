package com.dh.clinica.service.impl;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IService<Paciente> {


    private IPacienteRepository pacienteRepository;

    @Autowired
    public PacienteServiceImpl(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente salvar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Optional<Paciente> buscarPorNome(String name) {
        return pacienteRepository.findPacienteByNomeContainingIgnoreCase(name);
    }

    @Override
    public Paciente atualizar(Paciente paciente) {
        return pacienteRepository.saveAndFlush(paciente);
    }

    @Override
    public void excluir(Integer id) {
        pacienteRepository.deleteById(id);
    }
}

