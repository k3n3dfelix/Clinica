package com.dh.clinica.controller;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteServiceImpl pacienteServiceImpl;

    @Autowired
    public PacienteController(PacienteServiceImpl pacienteServiceImpl) {
        this.pacienteServiceImpl = pacienteServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        ResponseEntity<Paciente> response = null;
        if (!(paciente.getNome() == null || paciente.getSobrenome()== null || paciente.getRg()== null || paciente.getDataCadastro()== null || paciente.getEndereco() == null)){
            if (validacaoAtributo(paciente)){
                   pacienteServiceImpl.salvar(paciente);
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
        return ResponseEntity.ok(pacienteServiceImpl.buscarPorId(id).orElse(null));
    }

    @PutMapping
    public ResponseEntity<Paciente> atualizar(@RequestBody Paciente paciente) {
        if (paciente.getId() != null && pacienteServiceImpl.buscarPorId(paciente.getId()).isPresent())
            return ResponseEntity.ok(pacienteServiceImpl.atualizar(paciente));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir (@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (pacienteServiceImpl.buscarPorId(id).isPresent()) {
            pacienteServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Paciente excluído com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
         return response;
    }

    @GetMapping
    public ResponseEntity <List<Paciente>> buscarTodos () {
        return ResponseEntity.ok(pacienteServiceImpl.buscarTodos());
    }

    public boolean validacaoAtributo(Paciente paciente){
            if (paciente.getRg().isEmpty() || paciente.getRg().isBlank()){
                return false;
            }
        return true;
    }


}
