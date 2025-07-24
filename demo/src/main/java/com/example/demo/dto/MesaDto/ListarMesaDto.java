package com.example.demo.dto.MesaDto;

import java.util.List;

import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusMesa;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class ListarMesaDto {
    private Long id;
    private Integer numero;
    private Integer capacidade;
    private List<Reserva> reservas;
    private StatusMesa status;

}
