package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemMaisVendidoDto {
    
    private String nomeItem;
    private String categoria;
    private Long quantidadeVendida;
    private BigDecimal valorTotalVendido;
    private BigDecimal precoUnitario;
} 