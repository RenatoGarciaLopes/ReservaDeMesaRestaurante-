package com.example.demo.repository;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.HorarioFuncionamento;

@Repository
public interface IHorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Long>{

    Optional<HorarioFuncionamento> findByDiaFuncionamento(DayOfWeek dia);
}
