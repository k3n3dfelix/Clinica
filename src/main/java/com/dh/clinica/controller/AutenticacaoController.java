package com.dh.clinica.controller;


import com.dh.clinica.controller.dto.request.UsuarioRequest;
import com.dh.clinica.exceptions.InvalidDataException;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.model.dto.UsuarioDTO;
import com.dh.clinica.security.TokenDTO;
import com.dh.clinica.security.TokenService;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios_sec")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity logar(@RequestBody UsuarioDTO usuarioDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(usuarioDTO.getLogin(), usuarioDTO.getSenha());
        Authentication authenticate = manager.authenticate(token);
        String tokenJWT = tokenService.gerarToken((Usuario) authenticate.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody UsuarioRequest usuarioRequest) throws InvalidDataException {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(usuarioRequest));
    }

}
