package com.example.demo.dto.MesaDto;

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
    private StatusMesa status;
}
