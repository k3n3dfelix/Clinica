package com.dh.clinica.controller.dto.request.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoRequestUpdate {

    private Integer id;
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
}
