package com.dh.clinica.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoRequest {

    private String rua;
    private String numero;
    private String cidade;
    private String estado;
}
