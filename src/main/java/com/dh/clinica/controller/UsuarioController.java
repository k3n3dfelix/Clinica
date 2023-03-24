package com.dh.clinica.controller;

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

    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> listarTodos() {

        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Usuario>> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if(usuarioService.buscarPorId(id).isPresent()) {
            usuarioService.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario apagado com  sucesso!");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        ResponseEntity<Usuario> response;
        response = ResponseEntity.ok(usuarioService.cadastrar(usuario));

        return response;
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) throws Exception {
        ResponseEntity<Usuario> response;
        if (usuario.getId() != null && usuarioService.buscarPorId(usuario.getId()).isPresent())
            response = ResponseEntity.ok(usuarioService.atualizar(usuario));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           // throw new Exception("Registro não encontrado!");
        return response;
    }
}
