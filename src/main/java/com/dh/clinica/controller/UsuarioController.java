package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.controller.dto.response.UsuarioResponse;
import com.dh.clinica.controller.dto.request.update.UsuarioRequestUpdate;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.model.dto.UsuarioDTO;
import com.dh.clinica.security.TokenDTO;
import com.dh.clinica.security.TokenService;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    final static Logger log = Logger.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity logar(@RequestBody UsuarioDTO usuarioDTO) {
        log.debug("Realizando login do usuário " + usuarioDTO.toString());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(usuarioDTO.getLogin(), usuarioDTO.getSenha());
        Authentication authenticate = manager.authenticate(token);
        String tokenJWT = tokenService.gerarToken((Usuario) authenticate.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest request) throws InvalidDataException {
        log.debug("Salvando o usuário: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getEmail()== null || request.getLogin()== null|| request.getSenha()== null || request.getNivelAcesso() == null)){
            if (validacaoAtributo(request)){
                UsuarioResponse usuarioResponse = usuarioServiceImpl.salvar(request);
                response = ResponseEntity.ok(usuarioResponse);
                log.debug("Usuário salvo!");
            } else {
                throw new InvalidDataException("Um ou mais campos estão em branco ou vazio! Cadastro não realizado!");
            }} else {
            throw new InvalidDataException("Informações inválidas! Cadastro não realizado!");
        }
        return response;
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        log.debug("Buscando todos os usuários cadastrados...");
        List<UsuarioResponse> responses = usuarioServiceImpl.buscarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UsuarioResponse>> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.debug("Buscando o usuário com id: " + id);
        if(usuarioServiceImpl.buscarPorId(id).isPresent()) {
            log.debug("Usuário encontrado!");
        return ResponseEntity.ok(usuarioServiceImpl.buscarPorId(id));
        } else{
            throw new ResourceNotFoundException("Usuario não encontrado!");
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.debug("Buscando o(s) usuário(s): " + nome);
        List<UsuarioResponse> response;
        if (!usuarioServiceImpl.buscarPorNome(nome).isEmpty()) {
            log.debug("Usuário(s) encontrado(s)!");
            response = usuarioServiceImpl.buscarPorNome(nome);
        } else {
            throw new ResourceNotFoundException("Usuario não encontrado!");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login/{nome}")
    public ResponseEntity<UsuarioResponse> buscarPorLogin(@PathVariable String nome) throws ResourceNotFoundException {
        log.debug("Buscando o usuário: " + nome);
        UsuarioResponse response;
        if (usuarioServiceImpl.buscarPorLogin(nome) != null) {
            log.debug("Usuário encontrado!");
            response = usuarioServiceImpl.buscarPorLogin(nome);
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
            log.debug("Usuário excluído!");
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario excluído com sucesso!");
        }else{
            throw new ResourceNotFoundException("Usuario não encontrado!");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody UsuarioRequestUpdate request) throws ResourceNotFoundException, InvalidDataException {
        log.debug("Atualizando o usuário: " + request.toString());
        ResponseEntity response = null;
        if (!(request.getNome() == null || request.getEmail()== null || request.getLogin()== null|| request.getSenha()== null || request.getNivelAcesso() == null)){
            if ( usuarioServiceImpl.buscarPorId(request.getId()).isPresent()){
                response = ResponseEntity.ok(usuarioServiceImpl.atualizar(request));
                log.debug("Cadastro do usuário atualizado!");
            } else{
                throw new ResourceNotFoundException("Usuario não encontrado!");
            }
        } else {
            throw new InvalidDataException("Informações inválidas! Atualização do cadastro não realizada!");
            }
        return response;
    }

    public boolean validacaoAtributo(UsuarioRequest request){
        if (
                request.getNome().isEmpty() || request.getNome().isBlank() ||
                request.getLogin().isEmpty() || request.getLogin().isBlank() ||
                request.getEmail().isEmpty() || request.getEmail().isBlank() ||
                request.getSenha().isEmpty() || request.getSenha().isBlank() ||
                request.getNivelAcesso().isEmpty() || request.getNivelAcesso().isBlank()
        ){
            return false;
        }
        return true;
    }
}
