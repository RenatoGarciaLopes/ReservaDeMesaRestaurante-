package com.example.demo.dto.ClienteDto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor

public class ListarClienteDto {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDateTime dataCadastro;
    private String observacoes;
}
