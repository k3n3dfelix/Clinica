package com.dh.clinica.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioDTO {

    private String login;

    private String senha;
}
