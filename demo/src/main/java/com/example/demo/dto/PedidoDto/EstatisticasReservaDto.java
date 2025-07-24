package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class EstatisticasReservaDto {

    private Long reservaId;
    private Integer numeroMesa;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataReserva;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaReserva;
    
    private String nomeCliente;
    private Integer totalPedidos;
    private Integer totalItens;
    private BigDecimal valorTotal;
    private String statusReserva;
} 