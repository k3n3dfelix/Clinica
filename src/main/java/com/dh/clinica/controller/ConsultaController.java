package com.dh.clinica.controller;

import com.dh.clinica.model.Consulta;
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
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        log.debug("Salvando a consulta: " + consulta.toString());
        ResponseEntity<Consulta> response = null;
        if (!(consulta.getPaciente()== null || consulta.getDentista()== null || consulta.getDate()== null)){
            consultaServiceImpl.salvar(consulta);
            response = ResponseEntity.ok(consulta);
        } else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos(){
        log.debug("Buscando todas as consultas cadastradas...");
        return ResponseEntity.ok(consultaServiceImpl.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Consulta>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando a consulta com id: " + id);
        if(consultaServiceImpl.buscarPorId(id).isPresent()){
            return ResponseEntity.ok(consultaServiceImpl.buscarPorId(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id){
        log.debug("Excluindo a consulta com id: " + id);
        ResponseEntity<String> response;
        if(consultaServiceImpl.buscarPorId(id).isPresent()){
            consultaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Consulta excluída com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma consulta encontrada!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Consulta> atualizar(@RequestBody Consulta consulta){
        log.debug("Atualizando a consulta: " + consulta.toString());
        return ResponseEntity.ok(consultaServiceImpl.atualizar(consulta));
    }

}
