package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PedidoPorPeriodoDto {
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    
    private Long totalPedidos;
    private Long pedidosRealizados;
    private Long pedidosEntregues;
    private Long pedidosCancelados;
    private BigDecimal faturamento;
    private Long totalItens;
} 