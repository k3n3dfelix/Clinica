package com.dh.clinica.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
// @NoArgsConstructor
@ToString
public class Paciente {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String rg;
    private Date dataCadastro;
    private Endereco endereco;
}
