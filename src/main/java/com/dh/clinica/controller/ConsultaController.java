package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.ConsultaRequest;
import com.dh.clinica.controller.dto.response.ConsultaResponse;
import com.dh.clinica.controller.dto.request.update.ConsultaRequestUpdate;
import com.dh.clinica.controller.dto.response.ConsultaResponseCadastro;
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
    public ResponseEntity<ConsultaResponseCadastro> cadastrar(@RequestBody ConsultaRequest consulta) {
        log.debug("Salvando a consulta: " + consulta.toString());
        ResponseEntity<ConsultaResponseCadastro> response = null;
        if (!(consulta.getPaciente()== null || consulta.getDentista()== null || consulta.getDate()== null)){
            if(pacienteServiceImpl.buscarPorId(consulta.getPaciente().getId()).isPresent() && dentistaServiceImpl.buscarPorId(consulta.getDentista().getId()).isPresent()){
                ConsultaResponseCadastro consultaResponseCadastro = consultaServiceImpl.salvar(consulta);
                response = ResponseEntity.ok(consultaResponseCadastro);
            } else {
                response = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listarTodos() {
        log.debug("Buscando todos os consultas cadastrados...");
        ResponseEntity reponse = null;
        List<ConsultaResponse> responses = consultaServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ConsultaResponse>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando o consulta com id: " + id);
        return ResponseEntity.ok(consultaServiceImpl.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarConsulta(@PathVariable Integer id) {
        log.debug("Excluindo o consulta com id: " + id);
        ResponseEntity<String> response;
        if(consultaServiceImpl.buscarPorId(id).isPresent()) {
            consultaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Consulta excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<ConsultaResponse> atualizar(@RequestBody ConsultaRequestUpdate request) {
        log.debug("Atualizando o consulta: " + request.toString());
        ResponseEntity response = null;
        if (request.getId() != null && consultaServiceImpl.buscarPorId(request.getId()).isPresent())
            response = ResponseEntity.ok(consultaServiceImpl.atualizar(request));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

}
