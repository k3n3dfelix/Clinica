package com.dh.clinica.controller;

import com.dh.clinica.model.Usuario;
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

    final static Logger log = Logger.getLogger(PacienteController.class);

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping()
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        log.debug("Salvando o usuário: " + usuario.toString());
        ResponseEntity<Usuario> response = null;
        if (!(usuario.getNome() == null || usuario.getEmail()== null || usuario.getSenha()== null || usuario.getNivelAcesso() == null)){
            if (validacaoAtributo(usuario)){
                usuarioServiceImpl.salvar(usuario);
                response = ResponseEntity.ok(usuario);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        log.debug("Buscando todos os usuários cadastrados...");
        return ResponseEntity.ok(usuarioServiceImpl.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> buscarPorId(@PathVariable Integer id) {
        log.debug("Buscando o usuário com id: " + id);
        return ResponseEntity.ok(usuarioServiceImpl.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Optional<Usuario>> buscarPorNome(@PathVariable String nome){
        log.debug("Buscando o usuário: " + nome);
        return  ResponseEntity.ok(usuarioServiceImpl.buscarPorNome(nome));
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
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
        log.debug("Atualizando o usuário: " + usuario.toString());
        ResponseEntity<Usuario> response;
        if (usuario.getId() != null && usuarioServiceImpl.buscarPorId(usuario.getId()).isPresent())
            response = ResponseEntity.ok(usuarioServiceImpl.atualizar(usuario));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    public boolean validacaoAtributo(Usuario usuario){
        if (usuario.getNome().isEmpty() || usuario.getNome().isBlank()){
            return false;
        }
        return true;
    }
}
