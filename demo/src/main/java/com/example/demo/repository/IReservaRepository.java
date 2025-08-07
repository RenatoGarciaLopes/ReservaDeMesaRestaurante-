package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Reserva;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {

    Page<Reserva> findByClienteId(Long clienteId, Pageable pageable);
    
    List<Reserva> findByDataReservaBetween(LocalDate dataInicio, LocalDate dataFim);
    
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.funcionario = :funcionario AND r.dataReserva BETWEEN :dataInicio AND :dataFim")
    long countByFuncionarioAndDataReservaBetween(@Param("funcionario") Funcionario funcionario, 
                                                @Param("dataInicio") LocalDate dataInicio, 
                                                @Param("dataFim") LocalDate dataFim);

    @Query("SELECT r.horaReserva FROM Reserva r WHERE r.dataReserva = :data AND r.mesa.id = :mesaId")
    List<LocalTime> findHorariosReservadosPorDataEMesa(@Param("data") LocalDate data, @Param("mesaId") Long mesaId);
}