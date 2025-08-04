package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstatisticasFuncionariosDto {
    
    private Long totalFuncionarios;
    private Long funcionariosAtivos;
    private Long funcionariosInativos;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
    
    // Dados para gráficos
    private List<FuncionarioPerformanceDto> funcionariosMaisAtivos;
    private List<FuncionarioFaturamentoDto> funcionariosMaiorFaturamento;
} 