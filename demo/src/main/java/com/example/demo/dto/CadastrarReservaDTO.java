package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CadastrarReservaDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Mesa é obrigatória")
    private Long mesaId;

    private Long funcionarioId;

    @NotNull(message = "Data é obrigatória")
    @FutureOrPresent
    private LocalDate dataReserva;

    @NotNull(message = "Hora é obrigatória")
    @Schema(type = "string", example = "00:00", pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaReserva;

    @NotNull(message = "Quantidade de pessoas é obrigatório")
    @Min(value = 1)
    private Integer quantidadePessoas;
}
