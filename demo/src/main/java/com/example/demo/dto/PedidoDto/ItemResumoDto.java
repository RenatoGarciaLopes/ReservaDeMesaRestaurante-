package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ItemResumoDto {

    private String nomeItem;
    private Integer quantidade;
    private String categoria;
    private BigDecimal valorUnitario;
    private BigDecimal subTotal;
} 