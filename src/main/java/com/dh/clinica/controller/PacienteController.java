package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.PacienteRequest;
import com.dh.clinica.controller.dto.response.PacienteResponse;
import com.dh.clinica.controller.dto.request.update.PacienteRequestUpdate;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    final static Logger log = Logger.getLogger(PacienteController.class);

    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;

    @PostMapping()
    public ResponseEntity<PacienteResponse> cadastrar(@RequestBody PacienteRequest request) throws InvalidDataException {
        log.debug("Salvando o paciente: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getSobrenome()== null || request.getRg()== null || request.getDataCadastro()== null || request.getEndereco()== null)){
            if (validacaoAtributo(request)){
                PacienteResponse pacienteResponse = pacienteServiceImpl.salvar(request);
                response = ResponseEntity.ok(pacienteResponse);
                log.debug("Paciente salvo!");
            } else {
                throw new InvalidDataException("Um ou mais campos estão em branco ou vazio! Cadastro não realizado!");
            }} else {
            throw new InvalidDataException("Informações inválidas! Cadastro não realizado!");
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listarTodos() {
        log.debug("Buscando todos os pacientes cadastrados...");
        ResponseEntity reponse = null;
        List<PacienteResponse> responses = pacienteServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PacienteResponse>> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Buscando o paciente com id: " + id);
        if(pacienteServiceImpl.buscarPorId(id).isPresent()) {
            log.debug("Paciente encontrado!");
            return ResponseEntity.ok(pacienteServiceImpl.buscarPorId(id));
        } else{
            throw new ResourceNotFoundException("Paciente não encontrado!");
        }

    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<PacienteResponse>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.debug("Buscando o paciente: " + nome);

        List<PacienteResponse> responses = pacienteServiceImpl.buscarPorNome(nome);
        if (!pacienteServiceImpl.buscarPorNome(nome).isEmpty()) {
            log.debug("Usuário(s) encontrado(s)!");
            responses = pacienteServiceImpl.buscarPorNome(nome);
        } else {
            throw new ResourceNotFoundException("Paciente não encontrado!");
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPaciente(@PathVariable Integer id) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Excluindo o paciente com id: " + id);
        ResponseEntity<String> response;
        if(pacienteServiceImpl.buscarPorId(id).isPresent()) {
            try {
                pacienteServiceImpl.excluir(id);
            } catch (Exception e){
                throw new InvalidDataException("Erro! Paciente não foi excluído! Verifique se não há consultas cadastradas para esse paciente!");
            }
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Paciente excluído com sucesso!");
        }else{
            throw new ResourceNotFoundException("Paciente não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<PacienteResponse> atualizar(@RequestBody PacienteRequestUpdate request) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Atualizando o paciente: " + request.toString());
        ResponseEntity response = null;
        if (request.getNome() != null && pacienteServiceImpl.buscarPorId(request.getId()).isPresent()){


            if ( pacienteServiceImpl.buscarPorId(request.getId()).isPresent()){
                response = ResponseEntity.ok(pacienteServiceImpl.atualizar(request));
                log.debug("Cadastro do paciente atualizado!");
            } else{
                throw new ResourceNotFoundException("paciente não encontrado!");
            }
        } else {
            throw new InvalidDataException("Informações inválidas! Atualização do cadastro não realizada!");
        }


        return response;
    }

    public boolean validacaoAtributo(PacienteRequest request){
        if (request.getNome().isEmpty() || request.getNome().isBlank()){
            return false;
        }
        return true;
    }

}
