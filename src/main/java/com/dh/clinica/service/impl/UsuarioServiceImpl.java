package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.controller.dto.response.UsuarioResponse;
import com.dh.clinica.controller.dto.request.update.UsuarioRequestUpdate;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;
import com.dh.clinica.service.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse salvar(UsuarioRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = mapper.convertValue(request, Usuario.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        UsuarioResponse usuarioResponse = mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
        return usuarioResponse;
    }

    @Override
    public List<UsuarioResponse> buscarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(Usuario usuario: usuarios){
            responses.add(mapper.convertValue(usuario, UsuarioResponse.class));
        }
        return responses;
    }

    @Override
    public Optional<UsuarioResponse> buscarPorId(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        ObjectMapper mapper = new ObjectMapper();
        return usuario.map(usuario1 -> mapper.convertValue(usuario1, UsuarioResponse.class));
    }

    @Override
    public List<UsuarioResponse> buscarPorNome(String name) {
        List<Usuario> usuarios = usuarioRepository.findUsuarioByNomeContainingIgnoreCase(name);
        List<UsuarioResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Usuario usuario: usuarios){
            responses.add(mapper.convertValue(usuario, UsuarioResponse.class));
        }
        return responses;
    }

    @Override
    public UsuarioResponse buscarPorLogin(String name) {
        Usuario usuario = usuarioRepository.findUsuarioByLoginEquals(name);
        ObjectMapper mapper = new ObjectMapper();
         UsuarioResponse usuarioResponse = mapper.convertValue(usuario, UsuarioResponse.class);
        return usuarioResponse ;
    }

    @Override
    public UsuarioResponse atualizar(UsuarioRequestUpdate request) {
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = mapper.convertValue(request, Usuario.class);
        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
        UsuarioResponse usuarioResponse = mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
        return usuarioResponse;
    }

    @Override
    public void excluir(Integer id) {
        usuarioRepository.deleteById(id);
    }
}