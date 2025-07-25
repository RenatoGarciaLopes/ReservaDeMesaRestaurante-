package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Funcionario;

@Repository
public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {

    Optional<Funcionario> findByCpfAndAtivoTrue(String cpf);
    Optional<Funcionario> findByEmail(String email);
}
