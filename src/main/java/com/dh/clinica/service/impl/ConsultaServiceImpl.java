package com.dh.clinica.service.impl;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.repository.IConsultaRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements IService<Consulta> {

    private IConsultaRepository consultaRepository;

    @Autowired
    public ConsultaServiceImpl(IConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public Consulta salvar(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    @Override
    public List<Consulta> buscarTodos() {
        return consultaRepository.findAll();
    }

    @Override
    public Optional<Consulta> buscarPorId(Integer id) {
        return consultaRepository.findById(id);
    }

    @Override
    public Consulta atualizar(Consulta consulta) {
        return consultaRepository.saveAndFlush(consulta);
    }

    @Override
    public void excluir(Integer id) {
    consultaRepository.deleteById(id);
    }

}
