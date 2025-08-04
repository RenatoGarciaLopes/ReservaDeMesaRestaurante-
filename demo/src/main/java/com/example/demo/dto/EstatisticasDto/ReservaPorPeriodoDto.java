package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservaPorPeriodoDto {
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    
    private Long totalReservas;
    private Long reservasAtivas;
    private Long reservasCanceladas;
    private Long reservasConcluidas;
    private BigDecimal faturamento;
    private Double mediaPessoas;
} 