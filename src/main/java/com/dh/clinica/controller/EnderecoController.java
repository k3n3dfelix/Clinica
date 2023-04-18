package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.EnderecoRequest;
import com.dh.clinica.controller.dto.response.EnderecoResponse;
import com.dh.clinica.controller.dto.request.update.EnderecoRequestUpdate;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
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
    public ResponseEntity<EnderecoResponse> cadastrar(@RequestBody EnderecoRequest request) throws InvalidDataException{
        log.debug("Salvando o endereco: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getCidade() == null || request.getEstado() == null || request.getNumero()== null || request.getRua() == null)){
            if (validacaoAtributo(request)){
                EnderecoResponse enderecoResponse = enderecoServiceImpl.salvar(request);
                response = ResponseEntity.ok(enderecoResponse);
                log.debug("Endereço salvo!");
            } else {
                throw new InvalidDataException("Um ou mais campos estão em branco ou vazio! Cadastro não realizado!");
            }} else {
            throw new InvalidDataException("Informações inválidas! Cadastro não realizado!");
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
    public ResponseEntity<Optional<EnderecoResponse>> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Buscando o endereco com id: " + id);
        if(enderecoServiceImpl.buscarPorId(id).isPresent()) {
            log.debug("Endereço encontrado!");
            return ResponseEntity.ok(enderecoServiceImpl.buscarPorId(id));
        } else{
            throw new ResourceNotFoundException("Endereço não encontrado!");
        }
    }

    @GetMapping("/rua/{rua}")
    public ResponseEntity<List<EnderecoResponse>> buscarPorRua(@PathVariable String rua) throws ResourceNotFoundException {
        log.debug("Buscando o endereco: " + rua);
        List<EnderecoResponse> responses = enderecoServiceImpl.buscarPorRua(rua);
        if (!enderecoServiceImpl.buscarPorRua(rua).isEmpty()) {
            responses = enderecoServiceImpl.buscarPorRua(rua);
            log.debug("Endereço(s) encontrado(s)!");
        } else {
            throw new ResourceNotFoundException("Endereço não encontrado!");
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEndereco(@PathVariable Integer id) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Excluindo o endereco com id: " + id);
        ResponseEntity<String> response;
        if(enderecoServiceImpl.buscarPorId(id).isPresent()) {
            try {
                enderecoServiceImpl.excluir(id);
            } catch (Exception e){
                throw new InvalidDataException("Erro! Endereço não foi excluído! Verifique se não há pacientes cadastrados nesse endereço!");
            }
            enderecoServiceImpl.excluir(id);
            log.debug("Endereço excluído!");
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Endereco excluído com sucesso!");
        }else{
            throw new ResourceNotFoundException("Endereço não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<EnderecoResponse> atualizar(@RequestBody EnderecoRequestUpdate request) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Atualizando o endereco: " + request.toString());
        ResponseEntity response = null;
        if (request.getId() != null ){
            if ( enderecoServiceImpl.buscarPorId(request.getId()).isPresent()){
                response = ResponseEntity.ok(enderecoServiceImpl.atualizar(request));
                log.debug("Cadastro do endereço atualizado!");
            } else {
                throw new ResourceNotFoundException("Endereço não encontrado!");
            }
    } else {
        throw new InvalidDataException("Informações inválidas! Atualização do cadastro não realizada!");
    }
        return response;
    }

    public boolean validacaoAtributo(EnderecoRequest request){
        if (request.getRua().isEmpty() || request.getRua().isBlank()){
            return false;
        }
        return true;
    }

}