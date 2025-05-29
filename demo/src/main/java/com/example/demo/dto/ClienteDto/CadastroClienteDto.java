package com.example.demo.dto.ClienteDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter

public class CadastroClienteDto{
    
    @NotBlank(message = "O nome é obrigatorio")
    private String nome;

    @NotBlank(message = "O email é obrigado")
    @Email
    private String email;

    @NotBlank(message = "O telefone é obrigatorio")
    @Size(min = 11, max = 12, message = "siga o padrão a seguir:'44 99999999'" )
    private String telefone;
}