package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.DentistaRequest;
import com.dh.clinica.controller.dto.request.update.DentistaRequestUpdate;
import com.dh.clinica.controller.dto.response.DentistaResponse;
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
    public ResponseEntity<DentistaResponse> cadastrar(@RequestBody DentistaRequest request) {
        log.debug("Salvando o dentista: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getSobrenome()== null || request.getMatricula()== null)){
            if (validacaoAtributo(request)){
                DentistaResponse dentistaResponse = dentistaServiceImpl.salvar(request);
                response = ResponseEntity.ok(dentistaResponse);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Optional<DentistaResponse>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando o dentista com id: " + id);
        return ResponseEntity.ok(dentistaServiceImpl.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<DentistaResponse>> buscarPorNome(@PathVariable String nome){
        log.debug("Buscando o dentista: " + nome);
        ResponseEntity reponse = null;
        List<DentistaResponse> responses = dentistaServiceImpl.buscarPorNome(nome);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarDentista(@PathVariable Integer id) {
        log.debug("Excluindo o dentista com id: " + id);
        ResponseEntity<String> response;
        if(dentistaServiceImpl.buscarPorId(id).isPresent()) {
            dentistaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Dentista excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dentista não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<DentistaResponse> atualizar(@RequestBody DentistaRequestUpdate request) {
        log.debug("Atualizando o dentista: " + request.toString());
        ResponseEntity response = null;
        if (request.getNome() != null && dentistaServiceImpl.buscarPorId(request.getId()).isPresent())
            response = ResponseEntity.ok(dentistaServiceImpl.atualizar(request));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(DentistaRequest request){
        if (request.getNome().isEmpty() || request.getNome().isBlank()){
            return false;
        }
        return true;
    }


}