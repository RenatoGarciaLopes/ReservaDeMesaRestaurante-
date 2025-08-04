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
public class EstatisticasPedidosDto {
    
    private Long totalPedidos;
    private Long pedidosRealizados;
    private Long pedidosEntregues;
    private Long pedidosCancelados;
    private BigDecimal faturamentoTotal;
    private BigDecimal ticketMedio;
    private Long totalItensVendidos;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
    
    // Dados para gráficos
    private List<ItemMaisVendidoDto> itensMaisVendidos;
    private Map<String, Long> pedidosPorStatus;
    private Map<String, BigDecimal> faturamentoPorCategoria;
    private List<PedidoPorPeriodoDto> pedidosPorPeriodo;
} 