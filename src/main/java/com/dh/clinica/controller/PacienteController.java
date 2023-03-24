package com.dh.clinica.controller;


import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.repository.EnderecoDaoImpl;
import com.dh.clinica.repository.PacienteDaoImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService = new PacienteService(new PacienteDaoImpl(new EnderecoDaoImpl()));

    @PostMapping()
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        ResponseEntity<Paciente> response = null;
        if (!(paciente.getNome() == null || paciente.getSobrenome()== null || paciente.getRg()== null || paciente.getDataCadastro()== null || paciente.getEndereco() == null)){
            if (validacaoAtributo(paciente)){
                   pacienteService.cadastrar(paciente);
                   response = ResponseEntity.ok(paciente);
        } else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.buscar(id).orElse(null));
    }

    @PutMapping
    public ResponseEntity<Paciente> atualizar(@RequestBody Paciente paciente) {
        if (paciente.getId() != null && pacienteService.buscar(paciente.getId()).isPresent())
            return ResponseEntity.ok(pacienteService.atualizar(paciente));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir (@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (pacienteService.buscar(id).isPresent()) {
            pacienteService.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Paciente excluído com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
         return response;
    }

    @GetMapping
    public ResponseEntity <List<Paciente>> buscarTodos () {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    public boolean validacaoAtributo(Paciente paciente){
            if (paciente.getRg().isEmpty() || paciente.getRg().isBlank()){
                return false;
            }
        return true;
    }


}
