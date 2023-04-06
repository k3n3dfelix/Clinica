package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.EnderecoRequest;
import com.dh.clinica.controller.dto.response.EnderecoResponse;
import com.dh.clinica.service.impl.EnderecoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    final static Logger log = Logger.getLogger(EnderecoController.class);

    @Autowired
    private EnderecoServiceImpl enderecoServiceImpl;

    @PostMapping()
    public ResponseEntity<EnderecoResponse> cadastrar(@RequestBody EnderecoRequest request) {
        log.debug("Salvando o endereco: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getCidade() == null || request.getEstado() == null || request.getNumero()== null || request.getRua() == null)){
            if (validacaoAtributo(request)){
                EnderecoResponse enderecoResponse = enderecoServiceImpl.salvar(request);
                response = ResponseEntity.ok(enderecoResponse);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponse>> listarTodos() {
        log.debug("Buscando todos os enderecos cadastrados...");
        ResponseEntity reponse = null;
        List<EnderecoResponse> responses = enderecoServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EnderecoResponse>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando o endereco com id: " + id);
        return ResponseEntity.ok(enderecoServiceImpl.buscarPorId(id));
    }

    @GetMapping("/rua/{rua}")
    public ResponseEntity<List<EnderecoResponse>> buscarPorRua(@PathVariable String rua){
        log.debug("Buscando o endereco: " + rua);
        ResponseEntity reponse = null;
        List<EnderecoResponse> responses = enderecoServiceImpl.buscarPorRua(rua);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEndereco(@PathVariable Integer id) {
        log.debug("Excluindo o endereco com id: " + id);
        ResponseEntity<String> response;
        if(enderecoServiceImpl.buscarPorId(id).isPresent()) {
            enderecoServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Endereco excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<EnderecoResponse> atualizar(@RequestBody EnderecoRequest request) {
        log.debug("Atualizando o endereco: " + request.toString());
        ResponseEntity response = null;
        if (request.getId() != null && enderecoServiceImpl.buscarPorId(request.getId()).isPresent())
            response = ResponseEntity.ok(enderecoServiceImpl.atualizar(request));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(EnderecoRequest request){
        if (request.getRua().isEmpty() || request.getRua().isBlank()){
            return false;
        }
        return true;
    }

}