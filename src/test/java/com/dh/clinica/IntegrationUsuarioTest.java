package com.dh.clinica;

import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.controller.dto.response.UsuarioResponse;

import com.dh.clinica.service.impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationUsuarioTest {
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1DeveCadastrarUsuarioComSucesso() throws Exception {
        UsuarioRequest usuarioRequest = new UsuarioRequest("Guilherme", "gui@gmail.com","gui1", "12345", "adm");
        UsuarioResponse usuarioResponse = new UsuarioResponse(1,"Guilherme", "gui@gmail.com","gui1", "adm");

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String usuarioRequestJson = writer.writeValueAsString(usuarioRequest);
        String usuarioResponseJson = writer.writeValueAsString(usuarioResponse);

        MvcResult mvcResult = mockMvc.perform(post("/usuarios/cadastrar")
                .content(usuarioRequestJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        Assertions.assertEquals(usuarioResponseJson, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void test2DeveBuscarPacientePorIdOk() throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void test3BuscarPorIdNotFound() throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    public void test4DeletarUsuario() throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn();
    }

}
