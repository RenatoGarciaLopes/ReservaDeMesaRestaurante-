package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstatisticasGeraisDto {
    
    private Long totalReservas;
    private Long totalPedidos;
    private Long totalClientes;
    private Long totalFuncionarios;
    private BigDecimal faturamentoTotal;
    private BigDecimal ticketMedio;
    private Double taxaOcupacao;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
} 