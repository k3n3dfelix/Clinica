package com.dh.clinica.controller;

import com.dh.clinica.model.Endereco;
import com.dh.clinica.service.impl.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoServiceImpl enderecoService;


    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        return ResponseEntity.ok(enderecoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Endereco>> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @GetMapping("/rua/{rua}")
    public ResponseEntity<Optional<Endereco>> buscarPorNome(@PathVariable String rua){
        return  ResponseEntity.ok(enderecoService.buscarPorNome(rua));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEndereco(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if(enderecoService.buscarPorId(id).isPresent()) {
            enderecoService.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Endereço excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado!");
        }
        return response;
    }

    @PostMapping()
    public ResponseEntity<Endereco> cadastrar(@RequestBody Endereco endereco) {
        ResponseEntity<Endereco> response = null;
        if (!(endereco.getRua() == null || endereco.getNumero()== null || endereco.getCidade()== null || endereco.getEstado() == null)){
            if (validacaoAtributo(endereco)){
                enderecoService.salvar(endereco);
                response = ResponseEntity.ok(endereco);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Endereco> atualizar(@RequestBody Endereco endereco) {
        ResponseEntity<Endereco> response;
        if (endereco.getId() != null && enderecoService.buscarPorId(endereco.getId()).isPresent())
            response = ResponseEntity.ok(enderecoService.atualizar(endereco));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(Endereco endereco){
        if (endereco.getRua().isEmpty() || endereco.getRua().isBlank()){
            return false;
        }
        return true;
    }
}