package com.example.demo.dto.HorarioFuncionamentoDto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.example.demo.service.Utils.FormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ListarHorarioFuncionamento {

    private DayOfWeek diaFuncionamento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horarioInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horarioFim;

    public String getDiaFuncionamento() {
        return FormatUtils.formatarDiaFuncionamento(diaFuncionamento);
    }
}
