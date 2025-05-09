package com.example.demo.dto;

import com.example.demo.enums.StatusReserva;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class AtualizarStatusReservaDto {

    @NotNull(message = "Obrigatorio colocar o Status da reserva")
    private StatusReserva status;

}
