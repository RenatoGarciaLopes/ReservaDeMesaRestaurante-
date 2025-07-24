package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ListarItemPedidoDto {

    private String nomeItem;
    private Integer quantidade;
    private String imagemUrl;
    private String categoria;
    private BigDecimal valorUnitario;
    private BigDecimal subTotal;
}
