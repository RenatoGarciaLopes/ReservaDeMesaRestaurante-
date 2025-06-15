package com.example.demo.dto.CategoriaDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastrarCategoriaDto {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;
}
