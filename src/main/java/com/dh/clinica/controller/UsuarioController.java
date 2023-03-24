package com.dh.clinica.controller;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {

        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if(usuarioService.buscarPorId(id).isPresent()) {
            usuarioService.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario excluído com sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");
        }
        return response;
    }

    @PostMapping()
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        ResponseEntity<Usuario> response = null;
        if (!(usuario.getNome() == null || usuario.getEmail()== null || usuario.getSenha()== null || usuario.getNivelAcesso() == null)){
            if (validacaoAtributo(usuario)){
                usuarioService.cadastrar(usuario);
                response = ResponseEntity.ok(usuario);
            } else {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }} else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
        ResponseEntity<Usuario> response;
        if (usuario.getId() != null && usuarioService.buscarPorId(usuario.getId()).isPresent())
            response = ResponseEntity.ok(usuarioService.atualizar(usuario));
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
