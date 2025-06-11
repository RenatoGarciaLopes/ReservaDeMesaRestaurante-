package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enums.StatusReserva;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ListarReservaDto {

    private String nomeCliente;
    private Integer numeroMesa;
    private LocalDate dataReserva;
    private LocalTime horaReserva;
    private StatusReserva status;

}
