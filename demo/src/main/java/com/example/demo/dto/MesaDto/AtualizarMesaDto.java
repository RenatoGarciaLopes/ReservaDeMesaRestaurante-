package com.example.demo.dto.MesaDto;

import java.util.List;

import com.example.demo.entities.Reserva;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class AtualizarMesaDto {

    @Positive(message = "O número da mesa deve ser positivo")
    private Integer numero;

    @Min(value = 1, message = "A capacidade deve ser de pelo menos 1 pessoa")
    private Integer capacidade;

    private List<Reserva> reservas;
}
