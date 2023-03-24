package com.dh.clinica.controller;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.DentistaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    final static Logger log = Logger.getLogger(DentistaController.class);

    @Autowired
    private DentistaService dentistaService;

    @PostMapping
    public Dentista cadastrar(@RequestBody Dentista dentista) {
        return dentistaService.cadastrar(dentista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Dentista>> listarTodos () {
        return ResponseEntity.ok(dentistaService.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Dentista>> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(dentistaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deletarDentista(@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (dentistaService.buscarPorId(id).isPresent()) {
            dentistaService.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Dentista apagado com  sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum dentista encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Dentista> atualizar(@RequestBody Dentista dentista) throws Exception {
        ResponseEntity<Dentista> response;
        if (dentista.getId() != null && dentistaService.buscarPorId(dentista.getId()).isPresent())
            response = ResponseEntity.ok(dentistaService.atualizar(dentista));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }
}