package com.dh.clinica.service;

import com.dh.clinica.controller.UsuarioController;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Sql(scripts= "/sql/usuario_test.sql")
public class UsuarioTest {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    private UsuarioController usuarioController;


    @Test
    @Before
    public void buscarPorIdTest() {
        Optional<Usuario> usuario = usuarioServiceImpl.buscarPorId(18596);

        assertNotNull(usuario.isEmpty());
    }
    @Test
    @After
    public void deletarPorIdTest (){
        usuarioServiceImpl.excluir(2);

        Optional<Usuario> usuario = usuarioServiceImpl.buscarPorId(2);

        assertFalse(usuario.isPresent());
    }
}
