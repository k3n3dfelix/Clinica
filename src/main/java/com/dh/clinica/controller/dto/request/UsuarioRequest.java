package com.dh.clinica.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioRequest {

    private String nome;
    private String email;
    private String senha;
    private String nivelAcesso;

}

