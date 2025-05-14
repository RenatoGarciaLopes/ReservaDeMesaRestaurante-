package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.PedidoItem;

public interface IPedidoItemRepository extends JpaRepository<PedidoItem, Long>{

    void deleteAllByPedido_Id(Long pedidoId);
    boolean existsByItem_Id(Long idItem);
}
