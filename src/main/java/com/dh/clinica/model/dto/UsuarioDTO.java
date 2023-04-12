package com.dh.clinica.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UsuarioDTO {

    private String login;

    private String senha;
}
