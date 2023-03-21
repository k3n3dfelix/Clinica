package com.dh.clinica.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuario {

    private Integer id;

    private String nome;

    private String email;

    private String senha;

    private String nivelAcesso;
}
