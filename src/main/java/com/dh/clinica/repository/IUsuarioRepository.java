package com.dh.clinica.repository;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Endereco;
import com.dh.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findUsuarioByNomeContainingIgnoreCase(String nome);
}