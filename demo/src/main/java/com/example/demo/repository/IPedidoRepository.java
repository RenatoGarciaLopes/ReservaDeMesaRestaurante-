package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pedido;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido>{

    Page<Pedido> findByReservaId(Long reservaId, Pageable pageable);
}
