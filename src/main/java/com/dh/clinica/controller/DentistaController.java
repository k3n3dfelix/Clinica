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
    public List<Dentista> listarTodos () {
        return dentistaService.buscarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Dentista> buscarPorId(@PathVariable Integer id) {
        return dentistaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarDentista(@PathVariable Integer id) {
        dentistaService.excluir(id);
    }

    @PutMapping
    public Dentista atualizar(@RequestBody Dentista dentista) throws Exception {
        if (dentista.getId() != null && dentistaService.buscarPorId(dentista.getId()).isPresent())
            dentistaService.atualizar(dentista);
        else
            throw new Exception("Registro não encontrado!");
        return dentista;
    }
}
