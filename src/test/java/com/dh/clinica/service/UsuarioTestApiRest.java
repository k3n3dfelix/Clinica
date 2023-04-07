package com.dh.clinica.service;

import com.dh.clinica.controller.UsuarioController;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
//public class UsuarioTestApiRest {
//
//    @Mock
//    private UsuarioServiceImpl usuarioService;
//
//    @InjectMocks
//    private UsuarioController usuarioController;
//
//    private Usuario usuario;
//    private List<Usuario> usuarios;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        usuario = new Usuario();
//        usuario.setId(1);
//        usuario.setNome("Usuario1");
//        usuario.setEmail("usuario1@test.com");
//        usuario.setSenha("senha123");
//        usuario.setNivelAcesso("admin");
//
//        usuarios = new ArrayList<>();
//        usuarios.add(usuario);
//    }
//
//    @Test
//    public void testCadastrarUsuario() {
//        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuario);
//        ResponseEntity<Usuario> response = usuarioController.cadastrar(usuario);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(usuario, response.getBody());
//        verify(usuarioService, times(1)).salvar(any(Usuario.class));
//    }
//
//    @Test
//    public void testCadastrarUsuarioComAtributosNulos() {
//        usuario.setNome(null);
//        ResponseEntity<Usuario> response = usuarioController.cadastrar(usuario);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(usuarioService, times(0)).salvar(any(Usuario.class));
//    }
//
//    @Test
//    public void testCadastrarUsuarioComNomeVazio() {
//        usuario.setNome("");
//        ResponseEntity<Usuario> response = usuarioController.cadastrar(usuario);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(usuarioService, times(0)).salvar(any(Usuario.class));
//    }
//
//    @Test
//    public void testListarTodosUsuarios() {
//        when(usuarioService.buscarTodos()).thenReturn(usuarios);
//        ResponseEntity<List<Usuario>> response = usuarioController.listarTodos();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(usuarios, response.getBody());
//        verify(usuarioService, times(1)).buscarTodos();
//    }
//
//    @Test
//    public void testBuscarUsuarioPorIdExistente() {
//        when(usuarioService.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
//        ResponseEntity<Optional<Usuario>> response = usuarioController.buscarPorId(usuario.getId());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(usuario, response.getBody().get());
//        verify(usuarioService, times(1)).buscarPorId(usuario.getId());
//    }
//
//    @Test
//    public void testBuscarUsuarioPorIdInexistente() {
//        when(usuarioService.buscarPorId(usuario.getId())).thenReturn(Optional.empty());
//        ResponseEntity<Optional<Usuario>> response = usuarioController.buscarPorId(usuario.getId());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertFalse(response.getBody().isPresent());
//        verify(usuarioService, times(1)).buscarPorId(usuario.getId());
//    }
//
//    }

