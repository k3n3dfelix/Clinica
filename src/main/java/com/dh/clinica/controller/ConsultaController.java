package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.ConsultaRequest;
import com.dh.clinica.controller.dto.response.ConsultaResponse;
import com.dh.clinica.controller.dto.request.update.ConsultaRequestUpdate;
import com.dh.clinica.controller.dto.response.ConsultaResponseCadastro;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.impl.ConsultaServiceImpl;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    final static Logger log = Logger.getLogger(ConsultaController.class);
    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;
    @Autowired
    private DentistaServiceImpl dentistaServiceImpl;
    @Autowired
    private ConsultaServiceImpl consultaServiceImpl;

    @PostMapping
    public ResponseEntity<ConsultaResponseCadastro> cadastrar(@RequestBody ConsultaRequest consulta) throws InvalidDataException {
        log.debug("Salvando a consulta: " + consulta.toString());
        ResponseEntity<ConsultaResponseCadastro> response = null;
        if (!(consulta.getPaciente()== null || consulta.getDentista()== null || consulta.getDate()== null)){
            if(pacienteServiceImpl.buscarPorId(consulta.getPaciente().getId()).isPresent() && dentistaServiceImpl.buscarPorId(consulta.getDentista().getId()).isPresent()){
                ConsultaResponseCadastro consultaResponseCadastro = consultaServiceImpl.salvar(consulta);
                response = ResponseEntity.ok(consultaResponseCadastro);
                log.debug("Consulta salva!");
            } else {
                throw new InvalidDataException("O id do usuário e/ou dentista informado não existe ou um ou mais campos estão em branco ou vazio! Cadastro não realizado!");
            }} else {
            throw new InvalidDataException("Informações inválidas! Cadastro não realizado!");
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listarTodos() {
        log.debug("Buscando todas os consultas cadastrados...");
        List<ConsultaResponse> responses = consultaServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ConsultaResponse>> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Buscando o consulta com id: " + id);
        if (consultaServiceImpl.buscarPorId(id).isPresent()) {
            log.debug("Consulta encontrada!");
            return ResponseEntity.ok(consultaServiceImpl.buscarPorId(id));
        }else{
            throw new ResourceNotFoundException("Consulta não encontrada!");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarConsulta(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Excluindo o consulta com id: " + id);
        ResponseEntity<String> response;
        if(consultaServiceImpl.buscarPorId(id).isPresent()) {
            consultaServiceImpl.excluir(id);
            log.debug("Consulta excluída!");
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Consulta excluída com sucesso!");
        }else{
            throw new ResourceNotFoundException("Consulta não encontrada!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<ConsultaResponse> atualizar(@RequestBody ConsultaRequestUpdate consulta) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Atualizando o consulta: " + consulta.toString());
        ResponseEntity response = null;
        if (consulta.getId() != null && consultaServiceImpl.buscarPorId(consulta.getId()).isPresent()){
            if (!(consulta.getPaciente()== null || consulta.getDentista()== null || consulta.getDate()== null)){
                response = ResponseEntity.ok(consultaServiceImpl.atualizar(consulta));
                log.debug("As informações da consulta foram atualizadas!");
            } else{
                throw new InvalidDataException("Informações inválidas! A atualização da consulta não foi realizada!");
            }
        }else{
            throw new ResourceNotFoundException("Consulta não encontrada!");
        }
        return response;
    }

}
