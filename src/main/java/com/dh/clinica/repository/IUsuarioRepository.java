package com.dh.clinica.repository;

import com.dh.clinica.model.Endereco;
import com.dh.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}