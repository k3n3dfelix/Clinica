package com.dh.clinica.controller;

import com.dh.clinica.model.Paciente;
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

    private PacienteServiceImpl pacienteServiceImpl;

    @Autowired
    public PacienteController(PacienteServiceImpl pacienteServiceImpl) {
        this.pacienteServiceImpl = pacienteServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        log.debug("Salvando o paciente: " + paciente.toString());
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

    @GetMapping
    public ResponseEntity <List<Paciente>> buscarTodos () {
        log.debug("Buscando todos os pacientes cadastrados...");
        return ResponseEntity.ok(pacienteServiceImpl.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Integer id) {
        log.debug("Buscando o paciente com id: " + id);
        return ResponseEntity.ok(pacienteServiceImpl.buscarPorId(id).orElse(null));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Paciente>> buscarPorNome(@PathVariable String nome){
        log.debug("Buscando o paciente: " + nome);
        return  ResponseEntity.ok(pacienteServiceImpl.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir (@PathVariable Integer id) {
        log.debug("Excluindo o paciente com id: " + id);
        ResponseEntity<String> response = null;
        if (pacienteServiceImpl.buscarPorId(id).isPresent()) {
            pacienteServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Paciente excluído com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
         return response;
    }

    @PutMapping
    public ResponseEntity<Paciente> atualizar(@RequestBody Paciente paciente) {
        log.debug("Atualizando o paciente: " + paciente.toString());
        if (paciente.getId() != null && pacienteServiceImpl.buscarPorId(paciente.getId()).isPresent())
            return ResponseEntity.ok(pacienteServiceImpl.atualizar(paciente));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public boolean validacaoAtributo(Paciente paciente){
            if (paciente.getRg().isEmpty() || paciente.getRg().isBlank()){
                return false;
            }
        return true;
    }

}
