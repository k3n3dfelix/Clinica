package com.dh.clinica.controller.dto.request.update;

import com.dh.clinica.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteRequestUpdate {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String rg;
    private Date dataCadastro;
    private Endereco endereco;

}
