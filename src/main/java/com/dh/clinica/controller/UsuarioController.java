package com.dh.clinica.controller;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/buscar")
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Usuario> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Integer id) {
        usuarioService.excluir(id);
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return usuarioService.cadastrar(usuario);
    }

    @PutMapping
    public Usuario atualizar(@RequestBody Usuario usuario) throws Exception {
        if (usuario.getId() != null && usuarioService.buscarPorId(usuario.getId()).isPresent())
            usuarioService.atualizar(usuario);
        else
            throw new Exception("Registro não encontrado!");
        return usuario;
    }
}
