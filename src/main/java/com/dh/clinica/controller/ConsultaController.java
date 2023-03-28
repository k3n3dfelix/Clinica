package com.dh.clinica.controller;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.service.impl.ConsultaServiceImpl;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;
    @Autowired
    private DentistaServiceImpl dentistaServiceImpl;
    @Autowired
    private ConsultaServiceImpl consultaServiceImpl;

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        ResponseEntity<Consulta> response = null;
        if (!(consulta.getId() == null || consulta.getPaciente()== null || consulta.getDentista()== null || consulta.getDate()== null)){
                consultaServiceImpl.salvar(consulta);
                response = ResponseEntity.ok(consulta);
                } else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos(){
        return ResponseEntity.ok(consultaServiceImpl.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Consulta>> buscarPorId(@PathVariable Integer id) {
        if(consultaServiceImpl.buscarPorId(id).isPresent()){
            return ResponseEntity.ok(consultaServiceImpl.buscarPorId(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping
    public ResponseEntity<Consulta> atualizar(@RequestBody Consulta consulta){
        return ResponseEntity.ok(consultaServiceImpl.atualizar(consulta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id){
        ResponseEntity<String> response;
        if(consultaServiceImpl.buscarPorId(id).isPresent()){
            consultaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Consulta excluída com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma consulta encontrada!");
        }
        return response;
    }
}
