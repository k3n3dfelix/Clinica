package com.dh.clinica.controller;


import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.impl.EnderecoServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService = new PacienteService(new PacienteServiceImpl(new EnderecoServiceImpl()));

    @PostMapping()
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.cadastrar(paciente));
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.buscar(id).orElse(null));
    }

    @PutMapping
    public Paciente atualizar(@RequestBody Paciente paciente) throws Exception {
        if (paciente.getId() != null && pacienteService.buscar(paciente.getId()).isPresent())
            pacienteService.atualizar(paciente);
        else
            throw new Exception("Registro não encontrado!");
        return paciente;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir (@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (pacienteService.buscar(id).isPresent()) {
            pacienteService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Paciente excluído");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
         return response;
    }

    @GetMapping("/buscar")
    public ResponseEntity <List<Paciente>> buscarTodos () {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

}
