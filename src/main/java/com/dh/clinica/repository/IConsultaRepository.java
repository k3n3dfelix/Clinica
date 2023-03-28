package com.dh.clinica.repository;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsultaRepository extends JpaRepository<Consulta, Integer> {
}
