package com.dh.clinica.service;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioTest2 {

    @Mock
    private IUsuarioRepository usuarioRepositoryMock;

    private UsuarioServiceImpl usuarioService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepositoryMock);
    }

    @Test
    public void testSalvar() {
        // Given
        Usuario usuario = new Usuario();
        Usuario usuario2 = new Usuario();
        usuario.setNome("Fulano");
        usuario2.setNome("Beltrano");

        when(usuarioRepositoryMock.save(usuario)).thenReturn(usuario);

        // When
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        // Then
        assertEquals(usuario2, usuarioSalvo);
        verify(usuarioRepositoryMock, times(1)).save(usuario);
    }

    @Test
    public void testBuscarTodos() {
        // Given
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Fulano","kened@email.com", "1234", "admin"));
        usuarios.add(new Usuario("Fulano","kened@email.com", "1234", "admin"));

        when(usuarioRepositoryMock.findAll()).thenReturn(usuarios);

        // When
        List<Usuario> usuariosEncontrados = usuarioService.buscarTodos();

        // Then
        assertEquals(usuarios, usuariosEncontrados);
        verify(usuarioRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {
        // Given
        Usuario usuario = new Usuario("Fulano","kened@email.com", "1234", "admin");

        when(usuarioRepositoryMock.findById(1)).thenReturn(Optional.of(usuario));

        // When
        Optional<Usuario> usuarioEncontrado = usuarioService.buscarPorId(1);

        // Then
        assertEquals(Optional.of(usuario), usuarioEncontrado);
        verify(usuarioRepositoryMock, times(1)).findById(1);
    }

    @Test
    public void testBuscarPorNome() {
        // Given
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Fulano","kened@email.com", "1234", "admin"));
        usuarios.add(new Usuario("Fulano","kened@email.com", "1234", "admin"));

        when(usuarioRepositoryMock.findUsuarioByNomeContainingIgnoreCase("ful")).thenReturn(usuarios);

        // When
        List<Usuario> usuariosEncontrados = usuarioService.buscarPorNome("ful");

        // Then
        assertEquals(usuarios, usuariosEncontrados);
        verify(usuarioRepositoryMock, times(1)).findUsuarioByNomeContainingIgnoreCase("ful");
    }

    @Test
    public void testAtualizar() {
        // Given
        Usuario usuario = new Usuario("Fulano","kened@email.com", "1234", "admin");

        when(usuarioRepositoryMock.saveAndFlush(usuario)).thenReturn(usuario);

        // When
        Usuario usuarioAtualizado = usuarioService.atualizar(usuario);

        // Then
        assertEquals(usuario, usuarioAtualizado);
        verify(usuarioRepositoryMock, times(1)).saveAndFlush(usuario);
    }

    @Test
    public void testExcluir() {
        // Given
        Integer id = 1;

        // When
        usuarioService.excluir(id);

        // Then
        verify(usuarioRepositoryMock, times(1)).deleteById(id);
    }
}

