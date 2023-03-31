package com.dh.clinica.repository;

import com.dh.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

    List<Paciente> findPacienteByNomeContainingIgnoreCase(String nome);
}