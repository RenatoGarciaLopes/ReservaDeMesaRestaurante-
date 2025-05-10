package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pedido;

@Repository
public interface IPedido extends JpaRepository<Pedido, Long>{

}
