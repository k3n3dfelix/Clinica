package com.dh.clinica.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioResponse {

    private Integer id;

    private String nome;

    private String email;

    private String nivelAcesso;

}
