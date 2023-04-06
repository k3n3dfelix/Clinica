package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.PacienteRequest;
import com.dh.clinica.controller.dto.response.PacienteResponse;
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
    public ResponseEntity<PacienteResponse> cadastrar(@RequestBody PacienteRequest request) {
        log.debug("Salvando o paciente: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getSobrenome()== null || request.getId()== null || request.getRg()== null || request.getDataCadastro()== null || request.getEndereco()== null)){
            if (validacaoAtributo(request)){
                PacienteResponse pacienteResponse = pacienteServiceImpl.salvar(request);
                response = ResponseEntity.ok(pacienteResponse);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Optional<PacienteResponse>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando o paciente com id: " + id);
        return ResponseEntity.ok(pacienteServiceImpl.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<PacienteResponse>> buscarPorNome(@PathVariable String nome){
        log.debug("Buscando o paciente: " + nome);
        ResponseEntity reponse = null;
        List<PacienteResponse> responses = pacienteServiceImpl.buscarPorNome(nome);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPaciente(@PathVariable Integer id) {
        log.debug("Excluindo o paciente com id: " + id);
        ResponseEntity<String> response;
        if(pacienteServiceImpl.buscarPorId(id).isPresent()) {
            pacienteServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Paciente excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<PacienteResponse> atualizar(@RequestBody PacienteRequest request) {
        log.debug("Atualizando o paciente: " + request.toString());
        ResponseEntity response = null;
        if (request.getNome() != null && pacienteServiceImpl.buscarPorId(request.getId()).isPresent())
            response = ResponseEntity.ok(pacienteServiceImpl.atualizar(request));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(PacienteRequest request){
        if (request.getNome().isEmpty() || request.getNome().isBlank()){
            return false;
        }
        return true;
    }

}
