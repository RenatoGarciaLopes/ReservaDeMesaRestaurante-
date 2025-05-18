package com.example.demo.dto.ClienteDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor

public class ListarClienteDto {
    private String nome;
    private String email;
    private String telefone;
}
