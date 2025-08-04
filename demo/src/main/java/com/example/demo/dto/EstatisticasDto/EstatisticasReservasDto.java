package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstatisticasReservasDto {
    
    private Long totalReservas;
    private Long reservasAtivas;
    private Long reservasCanceladas;
    private Long reservasConcluidas;
    private Double taxaCancelamento;
    private Double mediaPessoasPorReserva;
    private BigDecimal faturamentoReservas;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
    
    // Dados para gráficos
    private Map<String, Long> reservasPorDiaSemana;
    private Map<String, Long> reservasPorHora;
    private Map<String, Long> reservasPorMes;
    private List<ReservaPorPeriodoDto> reservasPorPeriodo;
} 