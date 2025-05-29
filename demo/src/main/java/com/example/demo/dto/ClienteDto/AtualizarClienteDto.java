package com.example.demo.dto.ClienteDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter

public class AtualizarClienteDto {

    private String nome;
    @Email
    private String email;
    @Size(min = 11, max = 12, message = "O número de telefone deve conter entre 11 e 12 dígitos, seguindo o padrão: '44 999999999'")
    private String telefone; 
}
