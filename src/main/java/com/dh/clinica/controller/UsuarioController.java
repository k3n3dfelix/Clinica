package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.controller.dto.response.UsuarioResponse;
import com.dh.clinica.controller.dto.request.update.UsuarioRequestUpdate;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
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
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest request) throws InvalidDataException {
        log.debug("Salvando o usuário: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getEmail()== null || request.getSenha()== null || request.getNivelAcesso() == null)){
            if (validacaoAtributo(request)){
                UsuarioResponse usuarioResponse = usuarioServiceImpl.salvar(request);
                response = ResponseEntity.ok(usuarioResponse);
            } else {
                throw new InvalidDataException("O Atributo 'nome' está em branco ou vazio! Cadastro não realizado!");
            }} else {
            throw new InvalidDataException("Informações inválidas! Cadastro não realizado!");
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
    public ResponseEntity<Optional<UsuarioResponse>> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Buscando o usuário com id: " + id);
        if(usuarioServiceImpl.buscarPorId(id).isPresent()) {
        return ResponseEntity.ok(usuarioServiceImpl.buscarPorId(id));
        } else{
            throw new ResourceNotFoundException("Usuario não encontrado!");
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.debug("Buscando o usuário: " + nome);
        List<UsuarioResponse> response;
        if (!usuarioServiceImpl.buscarPorNome(nome).isEmpty()) {
            response = usuarioServiceImpl.buscarPorNome(nome);
        } else {
            throw new ResourceNotFoundException("Usuario não encontrado!");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Excluindo o usuário com id: " + id);
        ResponseEntity<String> response;
        if(usuarioServiceImpl.buscarPorId(id).isPresent()) {
                usuarioServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario excluído com sucesso!");
        }else{
            throw new ResourceNotFoundException("Usuario não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody UsuarioRequestUpdate request) throws ResourceNotFoundException {
        log.debug("Atualizando o usuário: " + request.toString());
        ResponseEntity response = null;
        if (request.getNome() != null && usuarioServiceImpl.buscarPorId(request.getId()).isPresent())
            response = ResponseEntity.ok(usuarioServiceImpl.atualizar(request));
        else
           throw new ResourceNotFoundException("Usuario não encontrado!");
        return response;
    }

    public boolean validacaoAtributo(UsuarioRequest request){
        if (request.getNome().isEmpty() || request.getNome().isBlank()){
            return false;
        }
        return true;
    }
}
