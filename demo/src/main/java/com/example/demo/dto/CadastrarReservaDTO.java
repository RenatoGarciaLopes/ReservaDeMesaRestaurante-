package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalDateTime dataReserva;

    @NotNull(message = "Hora Obrigatoria")
    @FutureOrPresent
    private LocalTime horaReserva;
}
