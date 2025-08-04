package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    Optional<Cliente> findByCpfAndAtivoTrue(String cpf);
    
    long countByAtivoTrue();
}
