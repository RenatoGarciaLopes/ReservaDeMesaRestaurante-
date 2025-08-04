package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pedido;
import com.example.demo.entities.Funcionario;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido>{

    Page<Pedido> findByReservaId(Long reservaId, Pageable pageable);
    
    @Query("SELECT p FROM Pedido p WHERE p.reserva.dataReserva BETWEEN :dataInicio AND :dataFim")
    List<Pedido> findByReservaDataReservaBetween(@Param("dataInicio") LocalDate dataInicio, 
                                                @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.funcionario = :funcionario AND p.reserva.dataReserva BETWEEN :dataInicio AND :dataFim")
    long countByFuncionarioAndReservaDataReservaBetween(@Param("funcionario") Funcionario funcionario, 
                                                       @Param("dataInicio") LocalDate dataInicio, 
                                                       @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT p FROM Pedido p WHERE p.funcionario = :funcionario AND p.reserva.dataReserva BETWEEN :dataInicio AND :dataFim")
    List<Pedido> findByFuncionarioAndReservaDataReservaBetween(@Param("funcionario") Funcionario funcionario, 
                                                              @Param("dataInicio") LocalDate dataInicio, 
                                                              @Param("dataFim") LocalDate dataFim);
}
