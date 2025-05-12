package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter

public class CadastroClienteDto{
    
    @NotNull(message = "O nome é obrigatorio")
    private Integer nome;

    @NotNull(message = "O email é obrigado")
    @Email
    private Integer email;

    @NotNull(message = "O telefone é obrigatorio")
    @Min(value = 11, message = "O telefone precisa ter no MINIMO 11 numero, siga o padrão a seguir: '44 99999999'")
    @Max(value = 12, message = "O telefone precisa ter no MAXIMO 12 numeros, siga o padrão a seguir:'44 99999999'")
    private Integer telefone;
}