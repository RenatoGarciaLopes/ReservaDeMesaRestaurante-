package com.example.demo.dto.HorarioFuncionamentoDto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ListarHorarioFuncionamento {

    private DayOfWeek diaFuncionamento;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
}
