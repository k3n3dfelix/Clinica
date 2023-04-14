package com.dh.clinica;

import com.dh.clinica.controller.dto.request.PacienteRequest;
import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.controller.dto.response.UsuarioResponse;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class IntegrationUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarUsuarioComSucesso() throws Exception {
        UsuarioRequest usuarioRequest = new UsuarioRequest("Guilherme", "gui@gmail.com","gui", "12345", "adm");
        UsuarioResponse usuarioResponse = new UsuarioResponse(1,"Guilherme", "gui@gmail.com","gui", "adm");

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
    public void deveBuscarPacientePorIdOk() throws Exception{
        UsuarioRequest usuarioRequest = new UsuarioRequest("Guilherme", "gui@gmail.com","gui", "12345", "adm");

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String usuarioRequestJson = writer.writeValueAsString(usuarioRequest);

        MvcResult mvcResult = mockMvc.perform(post("/usuarios/cadastrar")
                        .content(usuarioRequestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
