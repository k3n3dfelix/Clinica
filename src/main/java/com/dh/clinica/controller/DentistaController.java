package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.DentistaRequest;
import com.dh.clinica.controller.dto.request.update.DentistaRequestUpdate;
import com.dh.clinica.controller.dto.response.DentistaResponse;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
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

    @PostMapping()
    public ResponseEntity<DentistaResponse> cadastrar(@RequestBody DentistaRequest request) throws InvalidDataException{
        log.debug("Salvando o dentista: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getSobrenome()== null || request.getMatricula()== null)){
            if (validacaoAtributo(request)){
                DentistaResponse dentistaResponse = dentistaServiceImpl.salvar(request);
                response = ResponseEntity.ok(dentistaResponse);
                log.debug("Dentista salvo!");
            } else {
                throw new InvalidDataException("Um ou mais campos estão em branco ou vazio! Cadastro não realizado!");
            }} else {
            throw new InvalidDataException("Informações inválidas! Cadastro não realizado!");
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<DentistaResponse>> listarTodos() {
        log.debug("Buscando todos os dentistas cadastrados...");
        ResponseEntity reponse = null;
        List<DentistaResponse> responses = dentistaServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DentistaResponse>> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException{
        log.debug("Buscando o dentista com id: " + id);
        if(dentistaServiceImpl.buscarPorId(id).isPresent()) {
            log.debug("Dentista encontrado!");
            return ResponseEntity.ok(dentistaServiceImpl.buscarPorId(id));
        } else{
            throw new ResourceNotFoundException("Dentista não encontrado!");
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<DentistaResponse>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.debug("Buscando o dentista: " + nome);
        List<DentistaResponse> responses = dentistaServiceImpl.buscarPorNome(nome);
        if (!dentistaServiceImpl.buscarPorNome(nome).isEmpty()) {
            log.debug("Dentista(s) encontrado(s)!");
            responses = dentistaServiceImpl.buscarPorNome(nome);
        } else {
            throw new ResourceNotFoundException("Dentista não encontrado!");
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarDentista(@PathVariable Integer id) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Excluindo o dentista com id: " + id);
        ResponseEntity<String> response;
        if(dentistaServiceImpl.buscarPorId(id).isPresent()) {
            try {
                dentistaServiceImpl.excluir(id);
            } catch (Exception e){
                throw new InvalidDataException("Erro! Dentista não foi excluído! Verifique se não há consultas cadastradas para esse dentista!");
            }
            log.debug("Dentista excluído!");
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Dentista excluído com sucesso!");
        }else{
            throw new ResourceNotFoundException("Dentista não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<DentistaResponse> atualizar(@RequestBody DentistaRequestUpdate request) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Atualizando o dentista: " + request.toString());
        ResponseEntity response = null;
        if (request.getNome() != null){
            if ( dentistaServiceImpl.buscarPorId(request.getId()).isPresent()){
                response = ResponseEntity.ok(dentistaServiceImpl.atualizar(request));
                log.debug("Cadastro do dentista atualizado!");
            } else{
                throw new ResourceNotFoundException("Dentista não encontrado!");
            }
        } else {
            throw new InvalidDataException("Informações inválidas! Atualização do cadastro não realizada!");
        }
        return response;
    }

    public boolean validacaoAtributo(DentistaRequest request){
        if (request.getNome().isEmpty() || request.getNome().isBlank()){
            return false;
        }
        return true;
    }

}