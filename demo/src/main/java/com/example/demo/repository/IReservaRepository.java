package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Reserva;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Long>{

    boolean existsByMesa_Id(Long idMesa);  

}
