package com.dh.clinica.controller.dto.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DentistaRequestUpdate {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String matricula;

}
