package com.example.demo.dto.HorarioFuncionamentoDto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AtualizarHorarioFuncionamento {

    @Schema(type = "string", example = "00:00", pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioInicio;
    @Schema(type = "string", example = "00:00", pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioFim;
}
