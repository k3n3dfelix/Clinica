package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.controller.dto.response.UsuarioResponse;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    final static Logger log = Logger.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping()
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest request) {
        log.debug("Salvando o usuário: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getEmail()== null || request.getSenha()== null || request.getNivelAcesso() == null)){
            if (validacaoAtributo(request)){
                UsuarioResponse usuarioResponse = usuarioServiceImpl.salvar(request);
                response = ResponseEntity.ok(usuarioResponse);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        log.debug("Buscando todos os usuários cadastrados...");
        ResponseEntity reponse = null;
        List<UsuarioResponse> responses = usuarioServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UsuarioResponse>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando o usuário com id: " + id);
        return ResponseEntity.ok(usuarioServiceImpl.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNome(@PathVariable String nome){
        log.debug("Buscando o usuário: " + nome);
        ResponseEntity reponse = null;
        List<UsuarioResponse> responses = usuarioServiceImpl.buscarPorNome(nome);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer id) {
        log.debug("Excluindo o usuário com id: " + id);
        ResponseEntity<String> response;
        if(usuarioServiceImpl.buscarPorId(id).isPresent()) {
            usuarioServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody UsuarioRequest request) {
        log.debug("Atualizando o usuário: " + request.toString());
        ResponseEntity response = null;
        if (request.getNome() != null && usuarioServiceImpl.buscarPorId(request.getId()).isPresent())
            response = ResponseEntity.ok(usuarioServiceImpl.atualizar(request));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(UsuarioRequest request){
        if (request.getNome().isEmpty() || request.getNome().isBlank()){
            return false;
        }
        return true;
    }
}
