package com.dh.clinica.repository;

import com.dh.clinica.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEnderecoRepository extends JpaRepository<Endereco, Integer> {

    Optional<Endereco> findEnderecoByRuaContainingIgnoreCase(String rua);
}