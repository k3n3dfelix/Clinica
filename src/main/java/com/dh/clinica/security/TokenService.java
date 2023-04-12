package com.dh.clinica.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dh.clinica.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("]V)>j%n[Q^?mkzbLN&^/NkQ$#5myF`*;br'F&\\5qe)*'x^v3YVRd/9?~}MK&vQk7W`Y`+85n57cL;=rA,9X4+'xQuj/]buQ5{n$3'xLHhnUB>\"[^.zE.>\\>VNxM/vkyf");
            return JWT.create()
                    .withIssuer("API Dentista")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("]V)>j%n[Q^?mkzbLN&^/NkQ$#5myF`*;br'F&\\5qe)*'x^v3YVRd/9?~}MK&vQk7W`Y`+85n57cL;=rA,9X4+'xQuj/]buQ5{n$3'xLHhnUB>\"[^.zE.>\\>VNxM/vkyf");
            return JWT.require(algorithm)
                    .withIssuer("API Dentista")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
           throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
