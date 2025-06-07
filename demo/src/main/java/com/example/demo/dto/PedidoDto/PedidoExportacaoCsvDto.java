package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PedidoExportacaoCsvDto {

    @CsvBindByName(column = "Número da Mesa")
    private Integer numeroMesa;

    @CsvBindByName(column = "Data da Reserva")
    private String dataReserva;

    @CsvBindByName(column = "Hora da Reserva")
    private String horaReserva;

    @CsvBindByName(column = "Nome do Cliente")
    private String nomeCliente;

    @CsvBindByName(column = "Item")
    private String nomeItem;

    @CsvBindByName(column = "Quantidade")
    private Integer quantidade;

    @CsvBindByName(column = "Subtotal")
    private BigDecimal subTotal;

    @CsvBindByName(column = "Valor Total do Pedido")
    private BigDecimal valorTotal;
}
