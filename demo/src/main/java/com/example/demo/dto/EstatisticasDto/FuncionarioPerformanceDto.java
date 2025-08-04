package com.example.demo.dto.EstatisticasDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioPerformanceDto {
    
    private String nomeFuncionario;
    private String cargo;
    private Long totalPedidosProcessados;
    private Long totalReservasAtendidas;
    private Double mediaPedidosPorDia;
} 