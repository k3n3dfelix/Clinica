package com.dh.clinica.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Endereco {

    private Integer id;
    private String rua;
    private String numero;
    private String cidade;
    private String estado;

}
