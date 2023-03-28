package com.dh.clinica.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String sobrenome;
    private String rg;
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
