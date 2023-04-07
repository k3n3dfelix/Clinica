package com.dh.clinica.controller.dto.request.update;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaRequestUpdate {
    private Integer id;
    private Paciente paciente;
    private Dentista dentista;
    private Date date;

}
