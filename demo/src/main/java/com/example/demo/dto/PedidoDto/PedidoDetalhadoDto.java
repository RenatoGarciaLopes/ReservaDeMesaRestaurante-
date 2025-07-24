package com.example.demo.dto.PedidoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class PedidoDetalhadoDto {

    private Long id;
    private Integer numeroMesa;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataReserva;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaReserva;
    
    private String nomeCliente;
    private String nomeFuncionario;
    private List<ItemResumoDto> itens;
    private String observacoes;
    private BigDecimal valorTotal;
    private String status;
} 