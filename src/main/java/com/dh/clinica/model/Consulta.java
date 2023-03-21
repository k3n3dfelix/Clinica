package com.dh.clinica.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Consulta {

    private Integer id;

    private Paciente paciente;

    private Dentista dentista;

    private Date date;
}
