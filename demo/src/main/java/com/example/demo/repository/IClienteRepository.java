package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{



}
