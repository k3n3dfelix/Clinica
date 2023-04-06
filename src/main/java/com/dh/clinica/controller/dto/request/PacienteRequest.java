package com.dh.clinica.controller.dto.request;

import com.dh.clinica.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteRequest {

    private String nome;
    private String sobrenome;
    private String rg;
    private Date dataCadastro;
    private Endereco endereco;

}
