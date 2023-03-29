package com.dh.clinica.controller;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.impl.DentistaServiceImpl;
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
    private DentistaServiceImpl dentistaServiceImpl;

    @PostMapping
    public ResponseEntity<Dentista> cadastrar(@RequestBody Dentista dentista) {
        ResponseEntity<Dentista> response = null;
        if (!(dentista.getNome() == null || dentista.getSobrenome()== null || dentista.getMatricula()== null)){
            if (validacaoAtributo(dentista)){
                dentistaServiceImpl.salvar(dentista);
                response = ResponseEntity.ok(dentista);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Dentista>> listarTodos () {
        return ResponseEntity.ok(dentistaServiceImpl.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Dentista>> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(dentistaServiceImpl.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Optional<Dentista>> buscarPorNome(@PathVariable String nome){
        return  ResponseEntity.ok(dentistaServiceImpl.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deletarDentista(@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (dentistaServiceImpl.buscarPorId(id).isPresent()) {
            dentistaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Dentista excluído com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum dentista encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Dentista> atualizar(@RequestBody Dentista dentista) {
        ResponseEntity<Dentista> response;
        if (dentista.getId() != null && dentistaServiceImpl.buscarPorId(dentista.getId()).isPresent())
            response = ResponseEntity.ok(dentistaServiceImpl.atualizar(dentista));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(Dentista dentista){
        if (dentista.getMatricula().isEmpty() || dentista.getMatricula().isBlank()){
            return false;
        }
        return true;
    }
}