package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioFaturamentoDto {
    
    private String nomeFuncionario;
    private String cargo;
    private BigDecimal faturamentoTotal;
    private Long totalPedidos;
    private BigDecimal ticketMedio;
} 