package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class CadastrarReservaDTO {

    @NotNull(message = "Cliente Obrigatorio")
    private Long clienteId;

    @NotNull(message = "Mesa Obrigatoria")
    private Long mesaId;

    @NotNull(message = "Data Obrigatoria")
    @FutureOrPresent
    private LocalDate dataReserva;

    @NotNull(message = "Hora Obrigatoria")
    @Schema(type = "string", example = "00:00", pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaReserva;
}
