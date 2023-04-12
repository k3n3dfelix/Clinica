package com.dh.clinica.controller.dto.request.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioRequestUpdate {

    private Integer id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String nivelAcesso;

}

