package com.example.demo.dto.EstatisticasDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticasExportacaoCsvDto {

    @CsvBindByName(column = "Data da Reserva")
    private String dataReserva;

    @CsvBindByName(column = "Hora da Reserva")
    private String horaReserva;

    @CsvBindByName(column = "Número da Mesa")
    private Integer numeroMesa;

    @CsvBindByName(column = "Nome do Cliente")
    private String nomeCliente;

    @CsvBindByName(column = "Nome do Funcionário")
    private String nomeFuncionario;

    @CsvBindByName(column = "Cargo do Funcionário")
    private String cargoFuncionario;

    @CsvBindByName(column = "Quantidade de Pessoas")
    private Integer quantidadePessoas;

    @CsvBindByName(column = "Status da Reserva")
    private String statusReserva;

    @CsvBindByName(column = "Total de Pedidos")
    private Integer totalPedidos;

    @CsvBindByName(column = "Total de Itens")
    private Integer totalItens;

    @CsvBindByName(column = "Valor Total")
    private BigDecimal valorTotal;

    @CsvBindByName(column = "Status dos Pedidos")
    private String statusPedidos;
} 