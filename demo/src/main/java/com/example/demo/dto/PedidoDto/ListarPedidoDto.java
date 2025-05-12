package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ListarPedidoDto {

    private Integer numeroMesa;
    private LocalDateTime dataReserva;
    private LocalTime horaReserva;
    private String nomeCliente;
    private List<ListarItemPedidoDto> pedidos;
    private BigDecimal valorTotal; 
}
