package com.example.demo.dto.HorarioFuncionamentoDto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.example.demo.validation.IntervaloHorarioValido;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@IntervaloHorarioValido
public class CadastrarHorarioFuncionamento {

    @NotNull(message = "Dia da semana é obrigatório")
    private DayOfWeek diaFuncionamento;
    @Schema(type = "string", example = "00:00", pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "Hora de início de funcionamento é obrigatório")
    private LocalTime horarioInicio;
    @Schema(type = "string", example = "00:00", pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "Hora de fim de funcioanemnto é obrigatório")
    private LocalTime horarioFim;
}
