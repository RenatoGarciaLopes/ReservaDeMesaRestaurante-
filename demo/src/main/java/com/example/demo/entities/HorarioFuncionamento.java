package com.example.demo.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "horarios_funcionamento")
public class HorarioFuncionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek diaFuncionamento;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
}
