package com.dh.clinica.repository;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

    List<Paciente> findPacienteByNomeContainingIgnoreCase(String nome);
}