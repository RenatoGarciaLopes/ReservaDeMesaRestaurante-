package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.PedidoItem;

public interface IPedidoItemRepository extends JpaRepository<PedidoItem, Long>{

}
