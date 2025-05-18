package com.example.demo.dto.ClienteDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter

public class AtualizarClienteDto {

    private String nome;
    private String email;
    private String telefone; 
}
