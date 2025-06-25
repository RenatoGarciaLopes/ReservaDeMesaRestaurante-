package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.demo.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ListarPedidoDto {

    private Integer numeroMesa;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataReserva;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaReserva;
    private String nomeCliente;
    private String nomeFuncionario;
    private Cargo cargo;
    private List<ListarItemPedidoDto> pedidos;
    private String observacoes;
    private BigDecimal valorTotal; 
}
