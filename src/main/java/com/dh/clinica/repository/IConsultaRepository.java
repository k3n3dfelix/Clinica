package com.dh.clinica.repository;

import com.dh.clinica.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IConsultaRepository extends JpaRepository<Consulta, Integer> {

}
